package com.trzewik.activemq

import com.trzewik.activemq.grpc.InformationObserverMock
import com.trzewik.activemq.infrastructure.grpc.information.InformationDTO
import com.trzewik.activemq.interfaces.grpc.information.InformationControllerGrpc
import com.trzewik.activemq.interfaces.grpc.information.InformationForm
import com.trzewik.activemq.interfaces.grpc.information.InformationStreamControllerGrpc
import com.trzewik.activemq.jms.JmsTopicTestHelper
import io.grpc.stub.StreamObserver
import org.awaitility.Awaitility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import java.time.Duration

@SpringBootTest(
    classes = [App, GrpcTestConfig, JmsTestConfig],
    properties = [
        'jms.broker.url=vm://AppFT?broker.persistent=false',
        'jms.topic.information=AppFT.topic',
        'jms.queue.information=AppFT.queue',
        'jms.topic.virtual.information=VirtualTopic.AppFT.topic',
        'jms.queue.virtual.information=Consumer.AppFT.${jms.topic.virtual.information}'
    ]
)
@DirtiesContext
class AppFT extends Specification {
    @Autowired
    JmsTopicTestHelper jmsTopicTestHelperInformation
    @Autowired
    InformationStreamControllerGrpc.InformationStreamControllerStub informationStreamControllerStub
    @Autowired
    InformationControllerGrpc.InformationControllerBlockingStub informationControllerBlockingStub
    @Autowired
    List<StreamObserver<InformationDTO>> observers

    def 'should add new observer to observers list and send information after receiving new jms message on information topic'() {
        given:
            String id = 'example information controller id'
        and:
            InformationForm form = InformationForm.newBuilder()
                .setId(id)
                .build()
        and:
            InformationObserverMock observer = new InformationObserverMock()
        and:
            String message = 'Functional test message'
        when:
            informationStreamControllerStub.open(form, observer)
        then:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                assert observers.size() == 1
            }
        when:
            jmsTopicTestHelperInformation.sendMessageAndWaitForAppear(message)
        then:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                assert observer.information.size() == 1
            }
        and:
            observer.information.first().message == "Original: [${message}] with modification: [MODIFICATION]"
    }

    def 'should get information via grpc information controller'() {
        given:
            String id = 'example information controller id'
        and:
            InformationForm form = InformationForm.newBuilder()
                .setId(id)
                .build()
        when:
            InformationDTO response = informationControllerBlockingStub.getInformation(form)
        then:
            response.message == "message for: [${id}]"
    }
}
