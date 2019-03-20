package com.customer.app.inbound;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfiguration {
    @Value("${amq.brokerURL}")
    private String brokerUrl;
    @Value("${amq.userName}")
    private String userName;
    @Value("${amq.password}")
    private String password;

    @Bean(name = "activemq")
    ActiveMQComponent activeMQComponent() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
        JmsConfiguration configuration = new JmsConfiguration(factory);
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(configuration);
        return component;
    }

}
