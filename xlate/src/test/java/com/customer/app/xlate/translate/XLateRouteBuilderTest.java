package com.customer.app.xlate.translate;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.net.ConnectException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static com.customer.app.xlate.translate.XLateRouteBuilder.*;

public class XLateRouteBuilderTest extends CamelTestSupport {

    private static final String MOCK_RESULT = "mock:result";
    private static final String MOCK_FROM = "direct:mock-from";
    private static final String MOCK_DQL = "mock:dlq";
    private static final String DLQ_UNIT_TEST = "mock:dlq_unit_test";
    private static final String OUTPUT_UNIT_TEST = "mock:output_unit_test";
    private static final String INPUT_UNIT_TEST = "mock:input_unit_test";

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new XLateRouteBuilder();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties props = new Properties();
        props.put("input.queue", INPUT_UNIT_TEST);
        props.put("output.queue", OUTPUT_UNIT_TEST);
        props.put("dql.queue", DLQ_UNIT_TEST);
        return props;
    }

    @Test
    public void successTest() throws Exception {

        RouteDefinition route = context.getRouteDefinition(ROUTE_ID);

        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_RESULT, true);
        route.autoStartup(true)
                .adviceWith(context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        replaceFromWith(MOCK_FROM);

                        interceptSendToEndpoint(OUTPUT_UNIT_TEST)
                                .skipSendToOriginalEndpoint()
                                .to(mockEndpoint);
                    }
                });

        context.start();

        String body = new String(Files.readAllBytes(new File("src/test/resources/data/SimplePatient.xml").toPath()));

        mockEndpoint.expectedMessageCount(1);
        template.sendBody(MOCK_FROM, body);

        String executeMatchUpdate = mockEndpoint.getExchanges().get(0).getMessage().getBody(String.class);
        assertTrue(executeMatchUpdate.contains("<authUser>Xlate</authUser>"));
        assertTrue(executeMatchUpdate.contains("<firstName>First</firstName>"));
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testSimulateErrorUsingInterceptors() throws Exception {
        RouteDefinition route = context.getRouteDefinition(ROUTE_ID);

        MockEndpoint mockDLQ = getMockEndpoint(MOCK_DQL, true);

        SimulateErrorWithTranslation simulateErrorWithTranslation = new SimulateErrorWithTranslation();

        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(MOCK_FROM);
                // intercept sending to output queue and detour to our processor instead
                interceptSendToEndpoint(OUTPUT_UNIT_TEST)
                        .skipSendToOriginalEndpoint()
                        .process(simulateErrorWithTranslation);

                // intercept sending to dlq and detour to the mock instead
                interceptSendToEndpoint(DLQ_UNIT_TEST)
                        .skipSendToOriginalEndpoint()
                        .to(mockDLQ);
            }
        });
        context.start();
        mockDLQ.expectedMessageCount(1);

        // start the test by creating a file that gets picked up by the route
        String body = new String(Files.readAllBytes(new File("src/test/resources/data/SimplePatient.xml").toPath()));

        template.sendBody(MOCK_FROM, body);

        // assert our test passes
        assertMockEndpointsSatisfied();

        // processor should have 4 attempts because redelivery limit is set to 3.
        assertEquals(4, simulateErrorWithTranslation.getCounter());
    }

    private class SimulateErrorWithTranslation implements Processor {
        AtomicInteger counter = new AtomicInteger(0);
        public void process(Exchange exchange) throws Exception {
            // simulate the error by thrown the exception
            counter.addAndGet(1);
            throw new TypeConversionException("foo", String.class, new RuntimeException("foobar"));
        }

        public int getCounter() {
            return counter.get();
        }
    }
}