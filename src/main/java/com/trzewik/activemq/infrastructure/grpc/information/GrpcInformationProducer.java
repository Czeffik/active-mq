package com.trzewik.activemq.infrastructure.grpc.information;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class GrpcInformationProducer implements StreamInformationProducer {
    private final List<StreamObserver<InformationDTO>> observers = new LinkedList<>();

    @Override
    public void send(String message) {
        log.info("Notifying [{}] observers about new message: [{}]", observers.size(), message);
        observers.forEach(o -> {
            try {
                o.onNext(InformationDTO.newBuilder()
                    .setMessage(message)
                    .build());
            } catch (StatusRuntimeException ex) {
                log.info("Removing observer because: [{}]", ex.getMessage());
                log.debug(Arrays.toString(ex.getStackTrace()));
                remove(o);
            }
        });
    }

    @Override
    public synchronized void add(StreamObserver<InformationDTO> observer) {
        this.observers.add(observer);
    }

    private synchronized void remove(StreamObserver<InformationDTO> observer) {
        this.observers.remove(observer);
    }
}
