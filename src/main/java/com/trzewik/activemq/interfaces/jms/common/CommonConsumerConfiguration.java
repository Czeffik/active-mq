package com.trzewik.activemq.interfaces.jms.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.ConnectionFactory;

@Configuration
public class CommonConsumerConfiguration {
    @Bean
    DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory(
        ConnectionFactory activeMQConnectionFactory,
        DestinationResolver destinationResolver
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setDestinationResolver(destinationResolver);
        factory.setPubSubDomain(true);

        return factory;
    }

    @Bean
    DefaultJmsListenerContainerFactory queueJmsListenerContainerFactory(
        ConnectionFactory activeMQConnectionFactory,
        DestinationResolver destinationResolver
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setDestinationResolver(destinationResolver);

        return factory;
    }

    @Bean
    DestinationResolver destinationResolver() {
        return new DynamicDestinationResolver();
    }

    @Bean
    public ConnectionFactory activeMQConnectionFactory(
        @Value("${jms.broker.url}") String brokerUrl
    ) {
        return new ActiveMQConnectionFactory(brokerUrl);
    }
}
