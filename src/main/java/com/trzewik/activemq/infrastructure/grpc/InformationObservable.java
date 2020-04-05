package com.trzewik.activemq.infrastructure.grpc;

public interface InformationObservable {
    void add(InformationObserver observer);

    void delete(InformationObserver observer);
}
