package com.trzewik.activemq.infrastructure.grpc.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import io.grpc.stub.StreamObserver;

public interface StreamInformationProducer extends InformationProducer {
    void add(StreamObserver<InformationDTO> observer);
}
