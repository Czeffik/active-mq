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
class JmsTestConfig {

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsTopicTestHelper jmsTopicTestHelperInformation(ConnectionFactory testConnectionFactory, JmsTestProperties testJmsProperties) {
        return new JmsTopicTestHelper(testConnectionFactory, testJmsProperties.topicInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsTopicTestHelper jmsTopicTestHelperInformationVirtual(ConnectionFactory testConnectionFactory, JmsTestProperties testJmsProperties) {
        return new JmsTopicTestHelper(testConnectionFactory, testJmsProperties.topicVirtualInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsQueueTestHelper jmsQueueTestHelperInformation(ConnectionFactory testConnectionFactory, JmsTestProperties testJmsProperties) {
        return new JmsQueueTestHelper(testConnectionFactory, testJmsProperties.queueInformation)
    }

    @Lazy
    @Bean
    ConnectionFactory testConnectionFactory(JmsTestProperties testJmsProperties) {
        return new ActiveMQConnectionFactory(testJmsProperties.broker)
    }

    @Bean
    JmsTestProperties testJmsProperties(
        @Value('${jms.broker.url}') broker,
        @Value('${jms.topic.information}') infoTopic,
        @Value('${jms.topic.virtual.information}') infoVirtualTopic,
        @Value('${jms.queue.information}') infoQueue,
        @Value('${jms.queue.virtual.information}') infoVirtualQueue
    ) {
        return new JmsTestProperties(
            broker: broker,
            topicInformation: infoTopic,
            topicVirtualInformation: infoVirtualTopic,
            queueInformation: infoQueue,
            queueVirtualInformation: infoVirtualQueue
        )
    }
}
