package com.trzewik.activemq.grpc

import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer
import io.grpc.stub.StreamObserver

class StreamInformationProducerMock implements StreamInformationProducer {
    List<StreamObserver<InformationDTO>> observers = []

    void clear(){
        observers = []
    }

    @Override
    void add(StreamObserver<InformationDTO> observer) {
        observers << observer
    }

    @Override
    void send(String message) {
        observers.each {
            it.onNext(InformationDTO.newBuilder()
                .setMessage(message)
                .build())
        }
    }
}
