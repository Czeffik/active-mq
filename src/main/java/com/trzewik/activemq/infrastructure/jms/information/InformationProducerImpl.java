package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class InformationProducerImpl implements InformationProducer {
    private final JmsTemplate jmsTemplate;
    //TODO - probably in real application it will be required to have different producer for every case
    //in real application probably you will not send messages to three different destinations :D
    private final String queue;
    private final String topic;
    private final String virtualTopic;

    @Override
    public void send(String message) {
        log.info("Sending message: [{}], to queue: [{}]", message, queue);
        jmsTemplate.convertAndSend(resolveDestination(queue), message);
    }

    @Override
    public void publish(String message) {
        log.info("Publishing message: [{}], on topic: [{}]", message, topic);
        jmsTemplate.convertAndSend(resolveDestination(topic), message);
    }

    @Override
    public void publishOnVirtualTopic(String message) {
        log.info("Publishing message: [{}], on topic: [{}]", message, virtualTopic);
        jmsTemplate.convertAndSend(resolveDestination(virtualTopic), message);
    }

    //TODO should be moved to DestinationResolverClass  - create some naming convention
    private static ActiveMQDestination resolveDestination(String destination) {
        String upperCaseDestination = destination.toUpperCase();
        if (hasTopic(upperCaseDestination) && hasNotConsumer(upperCaseDestination)) {
            return new ActiveMQTopic(destination);
        }
        return new ActiveMQQueue(destination);
    }

    private static boolean hasTopic(String destination) {
        return destination.contains("TOPIC");
    }

    private static boolean hasNotConsumer(String destination) {
        return !destination.contains("CONSUMER");
    }
}
