package com.trzewik.activemq.infrastructure.grpc;

import com.trzewik.activemq.infrastructure.grpc.information.GrpcInformationProducer;
import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO;
import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer;
import io.grpc.stub.StreamObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GrpcInfrastructureConfiguration {
    @Bean
    StreamInformationProducer streamInformationProducer(List<StreamObserver<InformationDTO>> observers) {
        return new GrpcInformationProducer(observers);
    }
}
