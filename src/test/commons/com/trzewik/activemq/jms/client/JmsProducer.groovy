package com.trzewik.activemq.jms.client

import javax.jms.ConnectionFactory
import javax.jms.DeliveryMode
import javax.jms.Destination
import javax.jms.Message
import javax.jms.MessageProducer

class JmsProducer extends JmsClient {
    private final MessageProducer producer

    JmsProducer(ConnectionFactory factory, String destination) {
        super(factory, destination)
        this.producer = createProducer()
    }

    JmsProducer(ConnectionFactory factory, String destination, Credentials credentials) {
        super(factory, destination, credentials)
        this.producer = createProducer()
    }

    JmsProducer(ConnectionFactory factory, Destination destination) {
        super(factory, destination)
        this.producer = createProducer()
    }

    JmsProducer(ConnectionFactory factory, Destination destination, Credentials credentials) {
        super(factory, destination, credentials)
        this.producer = createProducer()
    }

    void send(String text) {
        producer.send(createTextMessage(text))
    }

    void send(Message message) {
        producer.send(message)
    }

    void send(Message message, int deliveryMode, int priority, long timeToLive){
        producer.send(message, deliveryMode, priority, timeToLive)
    }

    Message createTextMessage(String text) {
        return session.createTextMessage(text)
    }

    private MessageProducer createProducer() {
        MessageProducer producer = session.createProducer(destination)

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

        return producer
    }

    @Override
    void close() throws Exception {
        this.producer.close()
        super.close()
    }
}
