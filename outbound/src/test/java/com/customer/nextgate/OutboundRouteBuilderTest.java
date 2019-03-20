package com.customer.nextgate;

import com.sun.mdm.index.webservice.CallerInfo;
import com.sun.mdm.index.webservice.MatchColResult;
import com.sun.mdm.index.webservice.SystemPerson;
import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.cxf.message.MessageContentsList;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.net.ConnectException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static com.customer.nextgate.OutboundRouteBuilder.ROUTE_ID;

public class OutboundRouteBuilderTest extends CamelTestSupport {
    private static final String MOCK_RESULT = "mock:result";
    private static final String MOCK_FROM = "direct:mock-from";
    private static final String MOCK_DQL = "mock:dlq";
    private static final String DLQ_UNIT_TEST = "dlq_unit_test";
    private static final String INPUT_UNIT_TEST = "input_unit_test";


    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new OutboundRouteBuilder();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        // add the properties we want to override
        Properties props = new Properties();
        props.put("input.queue", INPUT_UNIT_TEST);
        props.put("nextgate.host", "nextgate.host");
        props.put("nextgate.port", "nextgate.port");
        props.put("dql.queue", DLQ_UNIT_TEST);

        return props;
    }

    @After
    public void cleanUp() throws Exception {
        context.stop();
    }

    @Test
    public void successTest() throws Exception {

        RouteDefinition route = context.getRouteDefinition(ROUTE_ID);

        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_RESULT, true);
        int value = 123;

        route.autoStartup(true)
                .adviceWith(context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        replaceFromWith(MOCK_FROM);

                        interceptSendToEndpoint("cxf:*")
                                .skipSendToOriginalEndpoint()
                                .to(mockEndpoint)
                                .process(exchange -> {
                                    Message out = new DefaultMessage(context);
                                    MatchColResult matchColResult = new MatchColResult();
                                    matchColResult.setResultCode(value);
                                    out.setBody(new MessageContentsList(matchColResult));
                                    exchange.setOut(out);
                                });

                    }
                });

        context.start();

        String body = new String(Files.readAllBytes(new File("src/test/resources/data/test-message-body.xml").toPath()));

        mockEndpoint.expectedMessageCount(1);
        Object result = template.sendBody(MOCK_FROM, ExchangePattern.InOut, body);
        assertEquals(result, value);

        List executeMatchUpdate = mockEndpoint.getExchanges().get(0).getMessage().getBody(List.class);
        assertEquals(executeMatchUpdate.get(0).getClass(), CallerInfo.class);
        assertEquals(executeMatchUpdate.get(1).getClass(), SystemPerson.class);
        assertEquals(executeMatchUpdate.size(), 2);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void failAndRetry() throws Exception {

        RouteDefinition route = context.getRouteDefinition(ROUTE_ID);

        MockEndpoint mockDLQ = getMockEndpoint(MOCK_DQL, true);

        SimulateErrorWithConnection simulateErrorWithConnection = new SimulateErrorWithConnection();


        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith(MOCK_FROM);

                interceptSendToEndpoint("cxf:*")
                        .skipSendToOriginalEndpoint()
                        .process(simulateErrorWithConnection);

                interceptSendToEndpoint("activemq:queue:" + DLQ_UNIT_TEST)
                        .skipSendToOriginalEndpoint()
                        .to(mockDLQ);

            }
        });

        context.start();

        mockDLQ.expectedMessageCount(1);

        // start the test by creating a file that gets picked up by the route
        String body = new String(Files.readAllBytes(new File("src/test/resources/data/test-message-body.xml").toPath()));

        template.sendBody(MOCK_FROM, body);

        // assert our test passes
        assertMockEndpointsSatisfied();

        // processor should have 4 attempts because redelivery limit is set to 3.
        assertEquals(4, simulateErrorWithConnection.getCounter());


    }

    private class SimulateErrorWithConnection implements Processor {

        AtomicInteger counter = new AtomicInteger(0);

        public void process(Exchange exchange) throws Exception {
            // simulate the error by thrown the exception
            counter.addAndGet(1);
            throw new ConnectException();
        }

        int getCounter() {
            return counter.get();
        }
    }

}