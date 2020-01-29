package com.company.app.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Value("${amq.brokerURL}")
    private String brokerUrl;
    @Value("${amq.userName}")
    private String userName;
    @Value("${amq.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "activemq")
    ActiveMQComponent activeMQComponent() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
        JmsConfiguration configuration = new JmsConfiguration(factory);
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(configuration);
        return component;
    }
}

