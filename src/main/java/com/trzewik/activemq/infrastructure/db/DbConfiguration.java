package com.trzewik.activemq.infrastructure.db;

import com.trzewik.activemq.domain.information.InformationRepository;
import com.trzewik.activemq.infrastructure.db.information.InformationRepositoryInMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {

    @Bean
    InformationRepository informationRepository() {
        return new InformationRepositoryInMemory();
    }
}
