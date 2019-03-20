package com.customer.app.inbound;

import com.customer.app.response.ESBResponse;
import com.redhat.usecase.service.ResultTransformer;
import org.apache.camel.ExchangePattern;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.nio.file.Files;
import java.util.Properties;

public class InboundRouteBuilderTest extends CamelTestSupport {

    private static final String MOCK_RESULT = "mock:result";
    private static final String MOCK_FROM = "direct:mock-from";

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws JAXBException {
        return new InboundRouteBuilder(new ResultTransformer(JAXBContext.newInstance(ESBResponse.class)));
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties props = new Properties();
        props.put("output.queue", "output");
        return props;
    }

    @Test
    public void successTest() throws Exception {

        RouteDefinition route = context.getRouteDefinition(InboundRouteBuilder.ROUTE_ID);

        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_RESULT, true);

        route.autoStartup(true)
                .adviceWith(context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        replaceFromWith(MOCK_FROM);

                        interceptSendToEndpoint("activemq:*")
                                .skipSendToOriginalEndpoint()
                                .to(mockEndpoint);
                    }
                });

        context.start();

        String body = new String(Files.readAllBytes(new File("src/test/resources/data/SimplePatient.xml").toPath()));
        mockEndpoint.expectedMessageCount(1);
        Object executeMatchUpdate = template.sendBody(MOCK_FROM, ExchangePattern.InOut, body);

        assertStringContains(executeMatchUpdate.toString(), "<Comment>DONE</Comment>");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void invalidXML() throws Exception {

        RouteDefinition route = context.getRouteDefinition(InboundRouteBuilder.ROUTE_ID);

        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_RESULT, true);

        route.autoStartup(true)
                .adviceWith(context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        replaceFromWith(MOCK_FROM);

                        interceptSendToEndpoint("activemq:*")
                                .skipSendToOriginalEndpoint()
                                .to(mockEndpoint);
                    }
                });

        context.start();

        String body = new String(Files.readAllBytes(new File("src/test/resources/data/Invalid.xml").toPath()));
        mockEndpoint.expectedMessageCount(0);
        Object executeMatchUpdate = template.sendBody(MOCK_FROM, ExchangePattern.InOut, body);

        assertStringContains(executeMatchUpdate.toString(), "<Comment>XML was invalid</Comment>");

        assertMockEndpointsSatisfied();
    }

}