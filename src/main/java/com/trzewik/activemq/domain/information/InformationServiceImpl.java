package com.trzewik.activemq.domain.information;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class InformationServiceImpl implements InformationService {
    private final InformationProducer producer;
    private final InformationRepository repository;

    private static String modifyMessage(String message) {
        //Doing some dummy modification of message
        return String.format("Received message: [%s] with service modification: [%s]", message, "MODIFICATION");
    }

    @Override
    public void notify(String message) {
        producer.send(modifyMessage(message));
    }

    @Override
    public void notifyAll(String message) {
        producer.publish(modifyMessage(message));
    }

    @Override
    public void notifyAllV2(String message) {
        producer.publishOnVirtualTopic(modifyMessage(message));
    }

    @Override
    public Information getInformation(String id) {
        return repository.getById(id);
    }
}
