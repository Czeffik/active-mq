package com.trzewik.activemq.interfaces.grpc.information;

import com.trzewik.activemq.domain.information.InformationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InformationControllerConfiguration {
    @Bean
    InformationController informationController(InformationService informationService) {
        return new InformationController(informationService);
    }
}
