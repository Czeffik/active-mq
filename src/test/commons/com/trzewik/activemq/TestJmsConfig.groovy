package com.trzewik.activemq

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
    Browser browserInfoQueue(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new Browser(testConnectionFactory, testJmsProperties.queueInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    Browser browserInfoVirtualQueue(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new Browser(testConnectionFactory, testJmsProperties.queueVirtualInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsConsumer consumerInformationTopic(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsConsumer(testConnectionFactory, testJmsProperties.topicInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsConsumer consumerInformationVirtualTopic(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsConsumer(testConnectionFactory, testJmsProperties.topicVirtualInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsProducer producerInformationTopic(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsProducer(testConnectionFactory, testJmsProperties.topicInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsProducer producerInformationVirtualTopic(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsProducer(testConnectionFactory, testJmsProperties.topicVirtualInformation)
    }

    @Lazy
    @Bean(destroyMethod = 'close')
    JmsProducer producerInformationQueue(ConnectionFactory testConnectionFactory, TestJmsProperties testJmsProperties) {
        return new JmsProducer(testConnectionFactory, testJmsProperties.queueInformation)
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
