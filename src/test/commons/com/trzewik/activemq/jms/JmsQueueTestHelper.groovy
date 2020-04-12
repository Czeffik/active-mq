package com.trzewik.activemq.jms

import com.trzewik.activemq.jms.client.Browser
import com.trzewik.activemq.jms.client.JmsClient
import com.trzewik.activemq.jms.client.JmsProducer
import org.awaitility.Awaitility

import javax.jms.ConnectionFactory
import javax.jms.Message
import javax.jms.Queue
import java.time.Duration

class JmsQueueTestHelper implements AutoCloseable {
    final JmsProducer producer
    final Browser browser
    final Duration duration

    JmsQueueTestHelper(ConnectionFactory factory, String queue, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, queue), new Browser(factory, queue), duration)
    }

    JmsQueueTestHelper(ConnectionFactory factory, Queue queue, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, queue), new Browser(factory, queue), duration)
    }

    JmsQueueTestHelper(ConnectionFactory factory, String queue, JmsClient.Credentials credentials, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, queue, credentials), new Browser(factory, queue, credentials), duration)
    }

    JmsQueueTestHelper(ConnectionFactory factory, Queue queue, JmsClient.Credentials credentials, Duration duration = Duration.ofSeconds(2)) {
        this(new JmsProducer(factory, queue, credentials), new Browser(factory, queue, credentials), duration)
    }

    JmsQueueTestHelper(JmsProducer producer, Browser browser, Duration duration) {
        this.duration = duration
        this.producer = producer
        this.browser = browser
    }

    void sendMessageAndWaitForAppear(Message message) {
        producer.send(message)
        Awaitility.await().atMost(duration).untilAsserted {
            browser.messages.contains(message)
        }
    }

    void sendMessageAndWaitForAppear(String message) {
        Message textMessage = producer.createTextMessage(message)

        producer.send(textMessage)
        Awaitility.await().atMost(duration).untilAsserted {
            browser.messages.contains(textMessage)
        }
    }

    @Override
    void close() throws Exception {
        producer.close()
        browser.close()
    }
}
