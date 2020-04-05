package com.trzewik.activemq.domain.information;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class InformationServiceImpl implements InformationService {
    private final List<InformationProducer> producers;
    private final InformationRepository repository;

    private static String modifyMessage(String message) {
        //Doing some dummy modification of message
        return String.format("Original: [%s] with modification: [%s]", message, "MODIFICATION");
    }

    @Override
    public void notifyAll(String message) {
        producers.forEach(p -> p.send(modifyMessage(message)));
    }

    @Override
    public Information getInformation(String id) {
        return repository.getById(id);
    }
}
