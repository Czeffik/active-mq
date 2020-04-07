package com.trzewik.activemq.infrastructure.grpc.information;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class GrpcInformationProducer implements StreamInformationProducer {
    private final List<StreamObserver<InformationDTO>> observers = new LinkedList<>();

    @Override
    public synchronized void send(String message) {
        log.info("Notifying [{}] observers about new message: [{}]", observers.size(), message);
        List<StreamObserver<InformationDTO>> inactiveObservers = new ArrayList<>();
        observers.forEach(o -> {
            try {
                o.onNext(ToInformationDTOConverter.from(message));
            } catch (StatusRuntimeException ex) {
                log.info("Scheduling observer to remove because: [{}]", ex.getMessage());
                log.debug(Arrays.toString(ex.getStackTrace()));
                inactiveObservers.add(o);
            }
        });
        inactiveObservers.forEach(this::remove);
    }

    @Override
    public synchronized void add(StreamObserver<InformationDTO> observer) {
        log.info("Adding new observer");
        this.observers.add(observer);
    }

    private synchronized void remove(StreamObserver<InformationDTO> observer) {
        log.info("Removing observer");
        this.observers.remove(observer);
    }
}
