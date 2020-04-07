package com.trzewik.activemq.infrastructure;

import com.trzewik.activemq.domain.information.InformationProducer;
import com.trzewik.activemq.domain.information.InformationRepository;
import com.trzewik.activemq.domain.information.InformationService;
import com.trzewik.activemq.domain.information.InformationServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainConfiguration {
    @Bean
    InformationService informationService(List<InformationProducer> informationProducers, InformationRepository informationRepository) {
        return InformationServiceFactory.create(informationProducers, informationRepository);
    }
}
