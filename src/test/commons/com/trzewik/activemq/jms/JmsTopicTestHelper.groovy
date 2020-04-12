package com.trzewik.activemq.jms

import com.trzewik.activemq.jms.client.JmsClient
import com.trzewik.activemq.jms.client.JmsConsumer
import com.trzewik.activemq.jms.client.JmsProducer
import org.awaitility.Awaitility

import javax.jms.ConnectionFactory
import javax.jms.Message
import javax.jms.TextMessage
import javax.jms.Topic
import java.time.Duration

class JmsTopicTestHelper implements AutoCloseable {
    final JmsProducer producer
    final JmsConsumer consumer
    final Duration duration

    JmsTopicTestHelper(ConnectionFactory factory, String topic, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, topic), new JmsConsumer(factory, topic), duration)
    }

    JmsTopicTestHelper(ConnectionFactory factory, Topic topic, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, topic), new JmsConsumer(factory, topic), duration)
    }

    JmsTopicTestHelper(ConnectionFactory factory, String topic, JmsClient.Credentials credentials, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, topic, credentials), new JmsConsumer(factory, topic, credentials), duration)
    }

    JmsTopicTestHelper(ConnectionFactory factory, Topic topic, JmsClient.Credentials credentials, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, topic, credentials), new JmsConsumer(factory, topic, credentials), duration)
    }

    JmsTopicTestHelper(JmsProducer producer, JmsConsumer consumer, Duration duration) {
        this.duration = duration
        this.producer = producer
        this.consumer = consumer
    }

    void sendMessageAndWaitForAppear(Message message) {
        producer.send(message)
        waitForMessageAppear(message)
    }

    void sendMessageAndWaitForAppear(String message) {
        Message textMessage = producer.createTextMessage(message)
        sendMessageAndWaitForAppear(textMessage)
    }

    void waitForTextMessageAppear(String message) {
        Awaitility.await().atMost(duration).untilAsserted {
            assert ((TextMessage) consumer.consume()).text == message
        }
    }

    void waitForMessageAppear(Message message) {
        Awaitility.await().atMost(duration).untilAsserted {
            assert consumer.consume() == message
        }
    }

    @Override
    void close() throws Exception {
        producer.close()
        consumer.close()
    }
}
