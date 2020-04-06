package com.trzewik.activemq.interfaces.grpc.information;

import com.trzewik.activemq.domain.information.Information;
import com.trzewik.activemq.domain.information.InformationService;
import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO;
import com.trzewik.activemq.infrastructure.grpc.information.ToInformationDTOConverter;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@RequiredArgsConstructor
@GRpcService
public class InformationController extends InformationControllerGrpc.InformationControllerImplBase {
    private final InformationService informationService;

    @Override
    public void getInformation(InformationForm request, StreamObserver<InformationDTO> responseObserver) {
        log.info("Receive GRPC request wit id: [{}]", request.getId());
        Information information = informationService.getInformation(request.getId());
        responseObserver.onNext(ToInformationDTOConverter.from(information));
        responseObserver.onCompleted();
    }
}
