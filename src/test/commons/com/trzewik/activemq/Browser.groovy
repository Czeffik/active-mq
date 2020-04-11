package com.trzewik.activemq

import javax.jms.ConnectionFactory
import javax.jms.Message
import javax.jms.Queue
import javax.jms.QueueBrowser

class Browser extends JmsClient {
    private final QueueBrowser browser

    Browser(ConnectionFactory factory, String queue) {
        super(factory, queue)
        validateDestination(queue)
        this.browser = createBrowser()
    }

    Browser(ConnectionFactory factory, String queue, Credentials credentials) {
        super(factory, queue, credentials)
        validateDestination(queue)
        this.browser = createBrowser()
    }

    Browser(ConnectionFactory factory, Queue queue) {
        super(factory, queue)
        this.browser = createBrowser()
    }

    Browser(ConnectionFactory factory, Queue queue, Credentials credentials) {
        super(factory, queue, credentials)
        this.browser = createBrowser()
    }

    boolean hasMessage(Message expectedMessage){
        return getMessages().contains(expectedMessage)
    }

    List<Message> getMessages(){
        Enumeration enumeration = browser.enumeration

        List<Message> messages = []
        while (enumeration.hasMoreElements()){
            messages << (Message) enumeration.nextElement()
        }

        return messages
    }

    private QueueBrowser createBrowser() {
        return session.createBrowser((Queue) destination)
    }

    private void validateDestination(String destination) {
        if (!isQueue(destination)) {
            super.close()
            throw new Exception("Destination must be queue! Your destination: [${destination}]")
        }
    }

    @Override
    void close() throws Exception {
        browser.close()
        super.close()
    }

    static class Exception extends RuntimeException {
        Exception(String msg) {
            super(msg)
        }
    }
}
