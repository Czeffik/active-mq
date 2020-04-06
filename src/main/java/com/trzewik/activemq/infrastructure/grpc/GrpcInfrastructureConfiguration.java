package com.trzewik.activemq.infrastructure.grpc;

import com.trzewik.activemq.infrastructure.grpc.information.GrpcInformationProducer;
import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcInfrastructureConfiguration {
    @Bean
    StreamInformationProducer informationObservableProducer() {
        return new GrpcInformationProducer();
    }
}
