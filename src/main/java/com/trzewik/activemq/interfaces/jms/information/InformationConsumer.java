package com.trzewik.activemq.interfaces.jms.information;

import com.trzewik.activemq.domain.information.InformationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class InformationConsumer {
    private final InformationService informationService;

    @JmsListener(destination = "${jms.topic.information}", containerFactory = "topicJmsListenerContainerFactory")
    public void subscribeTopic(String message) {
        log.info("Consumed message from information topic: [{}]", message);
        informationService.notifyAll(message);
    }

    @JmsListener(destination = "${jms.queue.information}", containerFactory = "queueJmsListenerContainerFactory")
    public void handleQueue(String message) {
        log.info("Consumed message from information queue: [{}]", message);
    }

    @JmsListener(destination = "${jms.queue.virtual.information}", containerFactory = "queueJmsListenerContainerFactory")
    public void handleVirtualTopic(String message) {
        log.info("Consumed message from information virtual topic: [{}]", message);
    }
}
