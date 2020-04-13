package com.trzewik.activemq.interfaces.grpc.information;

import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InformationStreamControllerConfiguration {
    @Bean
    InformationStreamController informationStreamController(StreamInformationProducer streamInformationProducer) {
        return new InformationStreamController(streamInformationProducer);
    }
}
