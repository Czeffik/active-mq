package com.trzewik.activemq.interfaces.grpc;

import com.trzewik.activemq.domain.information.InformationService;
import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer;
import com.trzewik.activemq.interfaces.grpc.information.InformationController;
import com.trzewik.activemq.interfaces.grpc.information.InformationStreamController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcInterfacesConfiguration {
    @Bean
    InformationStreamController informationStreamController(StreamInformationProducer streamInformationProducer) {
        return new InformationStreamController(streamInformationProducer);
    }

    @Bean
    InformationController informationController(InformationService informationService) {
        return new InformationController(informationService);
    }
}
