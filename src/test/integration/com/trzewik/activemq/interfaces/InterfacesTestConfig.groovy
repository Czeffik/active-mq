package com.trzewik.activemq.interfaces

import com.trzewik.activemq.domain.information.InformationService
import com.trzewik.activemq.grpc.StreamInformationProducerMock
import com.trzewik.activemq.infrastructure.grpc.information.StreamInformationProducer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Profile(InterfacesTestConfig.PROFILE)
@TestConfiguration
class InterfacesTestConfig {
    final static String PROFILE = 'interfaces-test'
    DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    InformationService informationServiceMock() {
        return factory.Mock(InformationService)
    }

    @Bean
    StreamInformationProducer streamInformationProducerMock() {
        return new StreamInformationProducerMock()
    }
}
