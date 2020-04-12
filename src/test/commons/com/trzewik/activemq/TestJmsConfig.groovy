package com.trzewik.activemq

import com.trzewik.activemq.jms.JmsQueueTestHelper
import com.trzewik.activemq.jms.JmsTopicTestHelper
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy

import javax.jms.ConnectionFactory

@TestConfiguration
class TestJmsConfig {

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsTopicTestHelper jmsTopicTestHelperInformation(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsTopicTestHelper(testConnectionFactory, testJmsProperties.topicInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsTopicTestHelper jmsTopicTestHelperInformationVirtual(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsTopicTestHelper(testConnectionFactory, testJmsProperties.topicVirtualInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsQueueTestHelper jmsQueueTestHelperInformation(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsQueueTestHelper(testConnectionFactory, testJmsProperties.queueInformation)
    }

    @Lazy
    @Bean
    ConnectionFactory testConnectionFactory(TestJmsProperties testJmsProperties) {
        return new ActiveMQConnectionFactory(testJmsProperties.broker)
    }

    @Bean
    TestJmsProperties testJmsProperties(
        @Value('${jms.broker.url}') broker,
        @Value('${jms.topic.information}') infoTopic,
        @Value('${jms.topic.virtual.information}') infoVirtualTopic,
        @Value('${jms.queue.information}') infoQueue,
        @Value('${jms.queue.virtual.information}') infoVirtualQueue
    ) {
        return new TestJmsProperties(
            broker: broker,
            topicInformation: infoTopic,
            topicVirtualInformation: infoVirtualTopic,
            queueInformation: infoQueue,
            queueVirtualInformation: infoVirtualQueue
        )
    }
}
