package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class JmsInformationProducer implements InformationProducer {
    private final JmsTemplate jmsTemplate;
    private final List<Destination> destinations;

    @Override
    public void send(String message) {
        log.info("Sending message: [{}], to: [{}]", message, destinations);
        destinations.forEach(d -> jmsTemplate.convertAndSend(d, message));
    }
}
