package com.customer.app.xlate.translate;

import org.apache.camel.LoggingLevel;
import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XLateRouteBuilder extends RouteBuilder {

    public static final String ROUTE_ID = "xlate";

    @Override
    public void configure() throws Exception {


        DefaultErrorHandlerBuilder errorHandlerBuilder = defaultErrorHandler()
                .redeliveryDelay(100)
                .maximumRedeliveries(3)
                .retryAttemptedLogLevel(LoggingLevel.WARN);

        errorHandler(errorHandlerBuilder);

        onException(TypeConversionException.class)
                .redeliveryPolicy(errorHandlerBuilder.getRedeliveryPolicy())
                .handled(true)
                .log("TypeConversionException occurred ${body}")
                .to("{{dql.queue}}");

        from("{{input.queue}}")
                .routeId(ROUTE_ID)
                .streamCaching()
                .unmarshal().jaxb("com.customer.app")
                .log(LoggingLevel.DEBUG, "after unmarshal")
                .convertBodyTo(com.sun.mdm.index.webservice.ExecuteMatchUpdate.class)
                .marshal()
                .jaxb("com.sun.mdm.index.webservice")
                .log(LoggingLevel.DEBUG, "after soap marshal ${body}")
                .inOnly("{{output.queue}}")
                .end();
    }
}
