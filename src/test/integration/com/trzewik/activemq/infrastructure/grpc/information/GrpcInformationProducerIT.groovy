package com.trzewik.activemq.infrastructure.grpc.information

import com.trzewik.activemq.GrpcTestConfig
import com.trzewik.activemq.grpc.InformationObserverFailingMock
import com.trzewik.activemq.grpc.InformationObserverMock
import com.trzewik.activemq.infrastructure.grpc.GrpcInfrastructureConfiguration
import io.grpc.stub.StreamObserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ActiveProfiles(['test'])
@ContextConfiguration(
    classes = [
        GrpcInfrastructureConfiguration,
        GrpcTestConfig
    ]
)
class GrpcInformationProducerIT extends Specification {
    @Autowired
    StreamInformationProducer streamInformationProducer
    @Autowired
    List<StreamObserver<InformationDTO>> observers

    def cleanup() {
        observers.clear()
    }

    def 'should add new observer to observers list'() {
        given:
            InformationObserverMock observer = new InformationObserverMock()
        when:
            streamInformationProducer.add(observer)
        then:
            observers.size() == 1
        and:
            observers.first() == observer
    }

    def 'should send message to all observers'() {
        given:
            InformationObserverMock observer1 = new InformationObserverMock()
            InformationObserverMock observer2 = new InformationObserverMock()
            InformationObserverMock observer3 = new InformationObserverMock()
            def allObservers = [observer1, observer2, observer3]
        and:
            allObservers.each { observers.add(it) }
        and:
            String message = 'TEST MESSAGE TO OBSERVERS'
        when:
            streamInformationProducer.send(message)
        then:
            allObservers.each { assert it.information.size() == 1 }
        and:
            allObservers.each { assert it.information.first().message == message }
        and:
            allObservers.each { assert !it.completed }
        and:
            allObservers.each { assert it.errors.size() == 0 }
    }

    def 'should send message to all observers and remove failing ones'() {
        given:
            def observer1 = new InformationObserverMock()
            def observer2 = new InformationObserverMock()
            def observer3 = new InformationObserverFailingMock()
        and:
            List<InformationObserverMock> passObservers = [observer1, observer2]
            List<StreamObserver<InformationDTO>> allObservers = [observer3]
            allObservers.addAll(passObservers)
        and:
            allObservers.each { observers.add(it) }
        and:
            String message = 'TEST MESSAGE TO OBSERVERS'
        when:
            streamInformationProducer.send(message)
        then:
            observers.size() == 2
        and:
            passObservers.each {
                assert it.information.size() == 1
                assert it.information.first().message == message
                assert !it.completed
                assert it.errors.size() == 0
            }
    }
}
