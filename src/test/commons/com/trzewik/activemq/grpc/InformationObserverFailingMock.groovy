package com.trzewik.activemq.grpc

import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver

class InformationObserverFailingMock implements StreamObserver<InformationDTO> {
    List<InformationDTO> information = []
    List<Throwable> errors = []
    boolean completed

    @Override
    void onNext(InformationDTO value) {
        throw new StatusRuntimeException(Status.UNAVAILABLE)
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
