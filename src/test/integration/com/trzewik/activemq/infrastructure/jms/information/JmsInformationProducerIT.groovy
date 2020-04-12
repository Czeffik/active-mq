package com.trzewik.activemq.infrastructure.jms.information

import com.trzewik.activemq.TestJmsConfig
import com.trzewik.activemq.domain.information.InformationProducer
import com.trzewik.activemq.jms.JmsQueueTestHelper
import com.trzewik.activemq.jms.JmsTopicTestHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ActiveProfiles(['test'])
@ContextConfiguration(classes = [
    JmsInformationProducerConfiguration,
    TestJmsConfig
])
@TestPropertySource(
    properties = [
        'jms.broker.url=vm://InformationProducerIT?broker.persistent=false',
        'jms.queue.information=InformationProducerIT.queue',
        'jms.topic.virtual.information=VirtualTopic.InformationProducerIT.topic'
    ]
)
@DirtiesContext
class JmsInformationProducerIT extends Specification {
    @Autowired
    InformationProducer jmsInformationProducer
    @Autowired
    JmsTopicTestHelper jmsTopicTestHelperInformationVirtual
    @Autowired
    JmsQueueTestHelper jmsQueueTestHelperInformation


    def 'should send message to information queue and information virtual topic'() {
        given:
            def message = 'message which should be send'
        when:
            jmsInformationProducer.send(message)
        then:
            jmsQueueTestHelperInformation.waitForTextMessageAppear(message)
        and:
            jmsTopicTestHelperInformationVirtual.waitForTextMessageAppear(message)
    }
}
