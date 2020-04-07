package com.trzewik.activemq.domain.information;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationServiceFactory {

    public static InformationService create(List<InformationProducer> informationProducers, InformationRepository informationRepository) {
        return new InformationServiceImpl(informationProducers, informationRepository);
    }
}
