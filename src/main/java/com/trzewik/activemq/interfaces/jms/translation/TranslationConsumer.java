package com.trzewik.activemq.interfaces.jms.translation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
class TranslationConsumer {
    @JmsListener(destination = "${jms.topic.information}", containerFactory = "topicJmsListenerContainerFactory")
    public void subscribe(String message) {
        log.info("Consumed message from information topic: {}", message);
    }

    @JmsListener(destination = "${jms.queue.information}", containerFactory = "queueJmsListenerContainerFactory")
    public void handle(String message) {
        log.info("Consumed message from information queue: {}", message);
    }

    @JmsListener(destination = "${jms.queue.virtual.translation}", containerFactory = "queueJmsListenerContainerFactory")
    public void handleVirtualTopic(String message) {
        log.info("Consumed message from information virtual topic: {}", message);
    }
}
