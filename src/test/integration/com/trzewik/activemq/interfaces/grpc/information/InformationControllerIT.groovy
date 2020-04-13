package com.trzewik.activemq.interfaces.grpc.information

import com.trzewik.activemq.TestGrpcConfig
import com.trzewik.activemq.domain.information.Information
import com.trzewik.activemq.domain.information.InformationService
import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import com.trzewik.activemq.interfaces.InterfacesTestConfig
import com.trzewik.activemq.interfaces.grpc.GrpcInterfacesConfiguration
import com.trzewik.activemq.interfaces.grpc.information.InformationControllerGrpc.InformationControllerBlockingStub
import io.grpc.Status
import io.grpc.StatusRuntimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles(['test', InterfacesTestConfig.PROFILE])
@SpringBootTest(
    classes = [GrpcInterfacesConfiguration, InterfacesTestConfig, TestGrpcConfig]
)
@DirtiesContext
class InformationControllerIT extends Specification {
    @Autowired
    InformationService informationServiceMock

    @Autowired
    InformationControllerBlockingStub informationControllerBlockingStub

    def 'should get information using information controller'() {
        given:
            String id = 'example information controller id'
            String message = 'TEST MESSAGE'
        and:
            Information returnedInformation = new Information(id, message)
            InformationForm form = InformationForm.newBuilder()
                .setId(id)
                .build()
        when:
            InformationDTO response = informationControllerBlockingStub.getInformation(form)
        then:
            1 * informationServiceMock.getInformation(id) >> returnedInformation
        and:
            response.message == message
    }

    def 'should handle exception'() {
        given:
            String id = 'test id'
            String exceptionMessage = 'SOME EXAMPLE EXCEPTION'
        and:
            InformationForm form = InformationForm.newBuilder()
                .setId(id)
                .build()
        when:
            informationControllerBlockingStub.getInformation(form)
        then:
            1 * informationServiceMock.getInformation(id) >> { throw new RuntimeException(exceptionMessage) }
        and:
            StatusRuntimeException ex = thrown()
            with(ex.status) {
                description == exceptionMessage
                code == Status.Code.INTERNAL
            }

    }
}
