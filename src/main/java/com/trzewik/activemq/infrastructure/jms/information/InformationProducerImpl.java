package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        jmsTemplate.convertAndSend(queue, message);
    }

    @Override
    public void publish(String message) {
        log.info("Publishing message: [{}], on topic: [{}]", message, topic);
        jmsTemplate.convertAndSend(topic, message);
    }

    @Override
    public void publishOnVirtualTopic(String message) {
        log.info("Publishing message: [{}], on topic: [{}]", message, virtualTopic);
        jmsTemplate.convertAndSend(virtualTopic, message);
    }
}
