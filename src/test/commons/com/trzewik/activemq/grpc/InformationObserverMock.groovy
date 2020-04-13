package com.trzewik.activemq.grpc

import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import io.grpc.stub.StreamObserver

class InformationObserverMock implements StreamObserver<InformationDTO> {
    List<InformationDTO> information = []
    List<Throwable> errors = []
    boolean completed

    @Override
    void onNext(InformationDTO value) {
        information << value
    }

    @Override
    void onError(Throwable t) {
        errors << t
    }

    @Override
    void onCompleted() {
        completed = true
    }

    void clear() {
        information = []
        errors = []
        completed = false
    }
}
