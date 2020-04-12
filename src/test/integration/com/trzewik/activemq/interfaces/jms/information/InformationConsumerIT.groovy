package com.trzewik.activemq.interfaces.jms.information

import com.trzewik.activemq.TestJmsConfig
import com.trzewik.activemq.domain.information.InformationService
import com.trzewik.activemq.interfaces.InterfacesTestConfig
import com.trzewik.activemq.jms.JmsQueueTestHelper
import com.trzewik.activemq.jms.JmsTopicTestHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ActiveProfiles(['test', InterfacesTestConfig.PROFILE])
@ContextConfiguration(classes = [
    InformationConsumerConfiguration,
    InterfacesTestConfig,
    TestJmsConfig
])
@TestPropertySource(
    properties = [
        'jms.broker.url=vm://InformationConsumerIT?broker.persistent=false',
        'jms.topic.information=InformationConsumerIT.topic',
        'jms.queue.information=InformationConsumerIT.queue',
        'jms.topic.virtual.information=VirtualTopic.InformationConsumerIT.topic',
        'jms.queue.virtual.information=Consumer.InformationConsumerIT.${jms.topic.virtual.information}',
    ]
)
class InformationConsumerIT extends Specification {

    @Autowired
    InformationService informationServiceMock
    @Autowired
    JmsTopicTestHelper jmsTopicTestHelperInformation
    @Autowired
    JmsTopicTestHelper jmsTopicTestHelperInformationVirtual
    @Autowired
    JmsQueueTestHelper jmsQueueTestHelperInformation

    def 'should subscribe and consume information message from information topic'() {
        given:
            String message = 'Test message to information topic'
        when:
            jmsTopicTestHelperInformation.sendMessageAndWaitForAppear(message)
        then:
            1 * informationServiceMock.notifyAll(message)
    }

    def 'should consume information message from information queue'() {
        given:
            String message = 'Test message to information queue'
        when:
            jmsQueueTestHelperInformation.sendMessageAndWaitForAppear(message)
        then:
            1 * informationServiceMock.getInformation(message)
    }

    def 'should consume information message from virtual information queue - message send to virtual topic'() {
        given:
            String message = 'Test message to information virtual topic'
        when:
            jmsTopicTestHelperInformationVirtual.sendMessageAndWaitForAppear(message)
        then:
            1 * informationServiceMock.getInformation(message)
    }
}
