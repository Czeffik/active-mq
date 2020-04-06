package com.trzewik.activemq.interfaces.grpc;

import com.trzewik.activemq.domain.information.InformationService;
import com.trzewik.activemq.interfaces.grpc.information.InformationController;
import com.trzewik.activemq.interfaces.grpc.information.InformationStreamController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfiguration {
    @Bean
    InformationStreamController informationStreamController() {
        return new InformationStreamController();
    }

    @Bean
    InformationController informationController(InformationService informationService) {
        return new InformationController(informationService);
    }
}
