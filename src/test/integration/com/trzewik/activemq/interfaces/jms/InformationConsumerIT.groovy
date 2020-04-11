package com.trzewik.activemq.interfaces.jms

import com.trzewik.activemq.Browser
import com.trzewik.activemq.JmsConsumer
import com.trzewik.activemq.JmsProducer
import com.trzewik.activemq.TestJmsConfig
import com.trzewik.activemq.domain.information.InformationService
import com.trzewik.activemq.interfaces.InterfacesTestConfig
import com.trzewik.activemq.interfaces.jms.information.InformationConsumerConfiguration
import org.awaitility.Awaitility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.time.Duration

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
    Browser browserInfoQueue
    @Autowired
    Browser browserInfoVirtualQueue
    @Autowired
    JmsConsumer consumerInformationTopic
    @Autowired
    JmsConsumer consumerInformationVirtualTopic
    @Autowired
    JmsProducer producerInformationTopic
    @Autowired
    JmsProducer producerInformationVirtualTopic
    @Autowired
    JmsProducer producerInformationQueue

    def 'should subscribe and consume information message from information topic'() {
        given:
            String message = 'Test message to information topic'
        when:
            producerInformationTopic.send(message)
        and:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                consumerInformationTopic.consume() != null
            }
        then:
            1 * informationServiceMock.notifyAll(message)
    }

    def 'should consume information message from information queue'() {
        given:
            String message = 'Test message to information queue'
        when:
            producerInformationQueue.send(message)
        and:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                browserInfoQueue.messages.size() == 1
            }
        then:
            1 * informationServiceMock.getInformation(message)
    }

    def 'should consume information message from virtual information queue - message send to virtual topic'() {
        given:
            String message = 'Test message to information virtual topic'
        when:
            producerInformationVirtualTopic.send(message)
        and:
            Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted {
                consumerInformationVirtualTopic.consume() != null
            }
        then:
            1 * informationServiceMock.getInformation(message)
    }
}
