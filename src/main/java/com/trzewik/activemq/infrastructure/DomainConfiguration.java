package com.trzewik.activemq.infrastructure;

import com.trzewik.activemq.domain.information.InformationProducer;
import com.trzewik.activemq.domain.information.InformationRepository;
import com.trzewik.activemq.domain.information.InformationService;
import com.trzewik.activemq.domain.information.InformationServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
    @Bean
    InformationService informationService(InformationProducer informationProducer, InformationRepository informationRepository) {
        return InformationServiceFactory.create(informationProducer, informationRepository);
    }
}
