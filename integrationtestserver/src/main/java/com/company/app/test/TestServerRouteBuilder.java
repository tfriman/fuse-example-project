package com.company.app.test;

import com.sun.mdm.index.webservice.PersonEJB;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestServerRouteBuilder extends RouteBuilder {
    @Autowired
    PersonEJB personEJB;

    @Override
    public void configure() throws Exception {
        from("cxf:bean:wsEndpoint")
                .streamCaching()
                .process(exchange -> {
                    Message exchangeIn = exchange.getIn();
                    MessageContentsList in = exchangeIn.getBody(MessageContentsList.class);
                    exchangeIn.setHeader("first", in.get(0));
                    exchangeIn.setHeader("second", in.get(1));
                })
                .bean(personEJB, "executeMatchUpdate(${header.first}, ${header.second})")
                .log(LoggingLevel.DEBUG, "Returning ${body}")
        ;
    }
}
