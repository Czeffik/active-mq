package com.trzewik.activemq.domain.information;

public interface InformationProducer {
    void send(String message);
}
