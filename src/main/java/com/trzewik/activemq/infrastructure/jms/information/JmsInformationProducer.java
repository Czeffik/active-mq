package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class JmsInformationProducer implements InformationProducer {
    private final JmsTemplate jmsTemplate;
    private final List<String> destinations;

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

    @Override
    public void send(String message) {
        log.info("Sending message: [{}], to: [{}]", message, destinations);
        destinations.forEach(d -> jmsTemplate.convertAndSend(resolveDestination(d), message));
    }
}
