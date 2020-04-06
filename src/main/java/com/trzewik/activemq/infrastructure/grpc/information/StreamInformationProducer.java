package com.trzewik.activemq.infrastructure.grpc.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import com.trzewik.activemq.interfaces.grpc.information.MessageResponse;
import io.grpc.stub.StreamObserver;

public interface StreamInformationProducer extends InformationProducer {
    void add(StreamObserver<MessageResponse> observer);
}
