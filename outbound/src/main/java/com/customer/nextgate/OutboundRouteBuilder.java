package com.customer.nextgate;

import org.apache.camel.Message;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.WARN;

@Component
public class OutboundRouteBuilder extends RouteBuilder {
    static final String ROUTE_ID = "outbound-route";

    @Override
    public void configure() {

        DefaultErrorHandlerBuilder errorHandlerBuilder = defaultErrorHandler()
                .redeliveryDelay(10)
                .retryAttemptedLogLevel(WARN)
                .maximumRedeliveries(3);

        errorHandler(errorHandlerBuilder);

        onException(ConnectException.class)
                .redeliveryPolicy(errorHandlerBuilder.getRedeliveryPolicy())
                .handled(true)
                .log("ConnectException occurred")
                .to("activemq:queue:{{dlq.queue}}");

        from("activemq:queue:{{input.queue}}")
                .routeId(ROUTE_ID)
                .streamCaching()
                .errorHandler(errorHandlerBuilder)
                .unmarshal().jaxb("com.sun.mdm.index.webservice")
                .log(DEBUG, "after unmarshal ${body}")
                .process(exchange -> {
                    Message exchangeIn = exchange.getIn();
                    com.sun.mdm.index.webservice.ExecuteMatchUpdate in = exchangeIn.getBody(com.sun.mdm.index.webservice.ExecuteMatchUpdate.class);
                    List<Object> list = new ArrayList<>();
                    list.add(in.getCallerInfo());
                    list.add(in.getSysObjBean());
                    exchangeIn.setBody(list);
                })
                .to("cxf://http://{{nextgate.host}}:{{nextgate.port}}/cxf/PersonEJBService/PersonEJB?serviceClass=com.sun.mdm.index.webservice.PersonEJB&wsdlURL=classpath:wsdl/EMPI_18080_2.wsdl"
                        + "&defaultOperationName=executeMatchUpdate")
                .transform(simple("${body[0].resultCode}"))
                .log("Result: ${body}")
                .end();

    }

}
