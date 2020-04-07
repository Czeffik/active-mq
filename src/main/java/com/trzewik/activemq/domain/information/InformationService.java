package com.trzewik.activemq.domain.information;

public interface InformationService {
    void notifyAll(String message);

    Information getInformation(String id);
}
