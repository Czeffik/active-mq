package com.trzewik.activemq.interfaces.jms.information;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
class InformationConsumer {

    @JmsListener(destination = "${jms.topic.information}", containerFactory = "topicJmsListenerContainerFactory")
    public void subscribeTopic(String message) {
        log.info("Consumed message from information topic: {}", message);
    }

    @JmsListener(destination = "${jms.queue.information}", containerFactory = "queueJmsListenerContainerFactory")
    public void handleQueue(String message) {
        log.info("Consumed message from information queue: {}", message);
    }

    @JmsListener(destination = "${jms.queue.virtual.information}", containerFactory = "queueJmsListenerContainerFactory")
    public void handleVirtualTopic(String message) {
        log.info("Consumed message from information virtual topic: {}", message);
    }
}
