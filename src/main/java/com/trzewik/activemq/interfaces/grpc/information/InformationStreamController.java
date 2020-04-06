package com.trzewik.activemq.interfaces.grpc.information;

import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@RequiredArgsConstructor
@GRpcService
public class InformationStreamController extends InformationStreamControllerGrpc.InformationStreamControllerImplBase {
    private final StreamInformationProducer streamInformationProducer;

    @Override
    public void open(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        log.info("Received GRPC request with id: [{}]", request.getId());
        streamInformationProducer.add(responseObserver);
    }
}
