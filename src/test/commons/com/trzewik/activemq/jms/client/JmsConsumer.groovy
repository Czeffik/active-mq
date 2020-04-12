package com.trzewik.activemq.jms.client

import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.MessageListener

class JmsConsumer extends JmsClient {
    private final MessageConsumer consumer

    JmsConsumer(ConnectionFactory factory, String destination) {
        super(factory, destination)
        this.consumer = createConsumer()
    }

    JmsConsumer(ConnectionFactory factory, String destination, Credentials credentials) {
        super(factory, destination, credentials)
        this.consumer = createConsumer()
    }

    JmsConsumer(ConnectionFactory factory, Destination destination) {
        super(factory, destination)
        this.consumer = createConsumer()
    }

    JmsConsumer(ConnectionFactory factory, Destination destination, Credentials credentials) {
        super(factory, destination, credentials)
        this.consumer = createConsumer()
    }

    void setMessageListener(MessageListener listener){
        consumer.setMessageListener(listener)
    }

    Message consume() {
        return consumer.receive()
    }

    Message consume(long receiveTimeout) {
        return consumer.receive(receiveTimeout)
    }

    Message consumeNoWait(){
        return consumer.receiveNoWait()
    }

    private MessageConsumer createConsumer() {
        return session.createConsumer(destination)
    }

    @Override
    void close() throws Exception {
        this.consumer.close()
        super.close()
    }
}
