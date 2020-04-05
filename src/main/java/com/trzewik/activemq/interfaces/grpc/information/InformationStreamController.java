package com.trzewik.activemq.interfaces.grpc.information;

import com.trzewik.activemq.infrastructure.grpc.InformationObserver;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@GRpcService
public class InformationStreamController extends InformationStreamControllerGrpc.InformationStreamControllerImplBase
    implements InformationObserver {

    //TODO this list should be somehow cleared when connection with client is finished
    private final List<StreamObserver<MessageResponse>> responseObservers = new ArrayList<>();

    @Override
    public void open(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        log.info("Received GRPC request with it: [{}]", request.getId());
        this.responseObservers.add(responseObserver);
    }

    @Override
    public void update(String message) {
        log.info("Sending message: [{}] using GRPC, to [{}] observers", message, responseObservers.size());
        responseObservers.forEach(o -> {
            o.onNext(
                MessageResponse.newBuilder()
                    .setMessage(message)
                    .build()
            );
        });
        //TODO shouldn't we end somehow our connection using: StreamObserver<MessageResponse>.onCompleted() ?
    }
}
