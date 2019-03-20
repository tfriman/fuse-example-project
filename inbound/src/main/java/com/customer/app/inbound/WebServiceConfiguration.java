package com.customer.app.inbound;

import com.customer.app.response.ESBResponse;
import com.redhat.usecase.service.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class WebServiceConfiguration {
    public static final String RESULT_JAXB_CONTEXT = "RESULT_JAXB_CONTEXT";

    @Bean
    @Qualifier(RESULT_JAXB_CONTEXT)
    JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(ESBResponse.class);
    }

    @Bean
    ResultTransformer resultTransformer(@Autowired @Qualifier(RESULT_JAXB_CONTEXT) JAXBContext jaxbContext) {
        return new ResultTransformer(jaxbContext);
    }

}
