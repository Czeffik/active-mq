package com.trzewik.activemq.domain.information;

public interface InformationService {
    void notify(String message);

    void notifyAll(String message);

    // XD - I know...
    void notifyAllV2(String message);
}
