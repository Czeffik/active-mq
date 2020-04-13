package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import java.util.Arrays;

@Configuration
public class JmsInformationProducerConfiguration {

    @Bean
    InformationProducer jmsInformationProducer(
        JmsTemplate jmsTemplate,
        Queue informationQueue,
        Topic informationVirtualTopic
    ) {
        return new JmsInformationProducer(
            jmsTemplate,
            Arrays.asList(informationQueue, informationVirtualTopic)
        );
    }

    @Bean
    Queue informationQueue(@Value("${jms.queue.information}") String queue) {
        return new ActiveMQQueue(queue);
    }

    @Bean
    Topic informationVirtualTopic(@Value("${jms.topic.virtual.information}") String virtualTopic) {
        return new ActiveMQTopic(virtualTopic);
    }

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
