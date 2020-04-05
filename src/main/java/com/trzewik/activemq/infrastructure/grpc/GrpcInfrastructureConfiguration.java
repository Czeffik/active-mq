package com.trzewik.activemq.infrastructure.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GrpcInfrastructureConfiguration {
    @Bean
    InformationObservableProducer informationObservableProducer(List<InformationObserver> informationObservers) {
        return new GrpcInformationProducer(informationObservers);
    }
}
