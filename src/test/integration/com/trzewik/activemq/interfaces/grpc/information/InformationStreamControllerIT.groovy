package com.trzewik.activemq.interfaces.grpc.information

import com.trzewik.activemq.GrpcTestConfig
import com.trzewik.activemq.grpc.InformationObserverMock
import com.trzewik.activemq.grpc.StreamInformationProducerMock
import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import com.trzewik.activemq.interfaces.InterfacesTestConfig
import com.trzewik.activemq.interfaces.grpc.GrpcInterfacesConfiguration
import io.grpc.stub.StreamObserver
import org.awaitility.Awaitility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.Duration

@ActiveProfiles(['test', InterfacesTestConfig.PROFILE])
@SpringBootTest(
    classes = [GrpcInterfacesConfiguration, InterfacesTestConfig, GrpcTestConfig]
)
@DirtiesContext
class InformationStreamControllerIT extends Specification {
    @Autowired
    StreamInformationProducerMock streamInformationProducerMock
    @Autowired
    InformationStreamControllerGrpc.InformationStreamControllerStub informationStreamControllerStub

    def 'should open information stream with async stub'() {
        given:
            String id = 'example information controller id'
        and:
            InformationForm form = InformationForm.newBuilder()
                .setId(id)
                .build()
        and:
            StreamObserver<InformationDTO> observer = new InformationObserverMock()
        when:
            informationStreamControllerStub.open(form, observer)
        then:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                assert streamInformationProducerMock.observers.size() == 1
            }
        cleanup:
            streamInformationProducerMock.clear()
            observer.clear()
    }

}
