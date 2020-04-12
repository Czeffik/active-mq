package com.trzewik.activemq.jms.client

import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.Session

/**
 * Oracle documentation for JMS: https://docs.oracle.com/cd/E19957-01/816-5904-10/816-5904-10.pdf
 */
abstract class JmsClient implements AutoCloseable {
    protected final Connection connection
    protected final Session session
    protected final Destination destination

    protected JmsClient(ConnectionFactory factory, String destination) {
        this(factory.createConnection(), destination)
    }

    protected JmsClient(ConnectionFactory factory, String destination, Credentials credentials) {
        this(factory.createConnection(credentials.username, credentials.password), destination)
    }

    protected JmsClient(ConnectionFactory factory, Destination destination) {
        this(factory.createConnection(), destination)
    }

    protected JmsClient(ConnectionFactory factory, Destination destination, Credentials credentials) {
        this(factory.createConnection(credentials.username, credentials.password), destination)
    }

    private JmsClient(Connection connection, Destination destination) {
        this.connection = connection
        this.connection.start()
        this.session = createSession()
        this.destination = destination
    }

    private JmsClient(Connection connection, String destination) {
        this.connection = connection
        this.connection.start()
        this.session = createSession()
        this.destination = createDestination(destination)
    }

    @Override
    void close() throws Exception {
        this.session.close()
        this.connection.close()
    }

    private Destination createDestination(String destination) {
        if (isQueue(destination)) {
            return session.createQueue(destination)
        }
        return session.createTopic(destination)
    }

    protected static boolean isTopic(String destination) {
        return destination.toUpperCase().contains('TOPIC')
    }

    protected static boolean isQueue(String destination) {
        String upperCaseDestination = destination.toUpperCase()
        return upperCaseDestination.contains('QUEUE') ||
            upperCaseDestination.matches('CONSUMER\\.[\\w\\W]{1,}\\.VIRTUALTOPIC\\.[\\w\\W]{1,}')
    }

    private Session createSession() {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
    }

    static class Credentials {
        String username
        String password
    }
}
