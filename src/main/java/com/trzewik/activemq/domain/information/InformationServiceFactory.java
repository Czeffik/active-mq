package com.trzewik.activemq.domain.information;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationServiceFactory {

    public static InformationService create(InformationProducer informationProducer, InformationRepository informationRepository) {
        return new InformationServiceImpl(informationProducer, informationRepository);
    }
}
