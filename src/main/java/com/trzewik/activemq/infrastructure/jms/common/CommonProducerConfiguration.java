package com.trzewik.activemq.infrastructure.jms.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class CommonProducerConfiguration {

    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory activeMQProducerConnectionFactory) {
        return new JmsTemplate(activeMQProducerConnectionFactory);
    }

    @Bean
    ConnectionFactory activeMQProducerConnectionFactory(
        @Value("${jms.broker.url}") String brokerUrl
    ) {
        return new ActiveMQConnectionFactory(brokerUrl);
    }
}
