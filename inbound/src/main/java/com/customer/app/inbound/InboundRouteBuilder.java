package com.customer.app.inbound;

import com.redhat.usecase.service.ResultTransformer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class InboundRouteBuilder extends RouteBuilder {

    public static final String ROUTE_ID = "inbound-route";

    private final ResultTransformer resultTransformer;
    private final String integrateRoute = "direct:integrateRoute";

    public InboundRouteBuilder(ResultTransformer resultTransformer) {
        this.resultTransformer = resultTransformer;
    }

    @Override
    public void configure() {
        onException(Throwable.class)
                .log("Exception occured ${exception.message}")
                .transform(constant("XML was invalid"))
                .handled(true)
                .bean(resultTransformer);

        rest("/demos")
                .consumes(MediaType.APPLICATION_XML_VALUE)
                .produces(MediaType.APPLICATION_XML_VALUE)
                .id("rest-route")
                .post("/match")
                .to(integrateRoute);

        from(integrateRoute)
                .routeId(ROUTE_ID)
                .streamCaching()
                .log(INFO, "inbound direct:integrateRoute called ${body}")
                .to("validator:schemas/PatientDemographics.xsd")
                .log("After validator ${body}")
                .marshal().jaxb("com.customer.app")
                .log(DEBUG, "AFTER JAXB: ${body}")
                .inOnly("activemq:queue:{{output.queue}}")
                .transform(constant("DONE"))
                .bean(resultTransformer);
    }
}
