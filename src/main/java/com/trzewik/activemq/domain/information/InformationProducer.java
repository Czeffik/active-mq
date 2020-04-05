package com.trzewik.activemq.domain.information;

//TODO - in real application this names probably wouldn't have information about destination
//TODO - but in this example it gives possibility to simplify
public interface InformationProducer {
    void send(String message);

    void publish(String message);

    //from application perspective this method is the same like `publish(String)` but for this POC it makes development easier
    void publishOnVirtualTopic(String message);
}
