package com.redhat.usecase.service;

import com.customer.app.response.ESBResponse;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Creates the output message.
 */
@Slf4j
public class ResultTransformer {

    private final JAXBContext jaxbContext;

    public ResultTransformer(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }

    public String transform(String comment) {

        try {
            ESBResponse esbResponse = new ESBResponse();
            esbResponse.setBusinessKey(UUID.randomUUID().toString());
            esbResponse.setPublished(true);

            esbResponse.setComment(comment);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(esbResponse, stringWriter);
            return stringWriter.toString();

        } catch (Exception e) {
            log.error("ups {}", e);
            // TODO what
            return null;
        }
    }
}
