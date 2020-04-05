package com.trzewik.activemq.infrastructure.grpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class GrpcInformationProducer implements InformationObservableProducer {
    private final List<InformationObserver> observers;

    @Override
    public void send(String message) {
        log.info("Notifying observers about new message: [{}]", message);
        log.info("Number of observers: [{}]", observers.size());
        observers.forEach(o -> o.update(message));
    }

    @Override
    public synchronized void add(InformationObserver observer) {
        observers.add(observer);
    }

    @Override
    public synchronized void delete(InformationObserver observer) {
        observers.add(observer);
    }
}
