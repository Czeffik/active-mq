package com.trzewik.activemq.infrastructure.jms;

import com.trzewik.activemq.infrastructure.jms.information.JmsInformationProducerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    JmsInformationProducerConfiguration.class
})
@Configuration
public class JmsInfrastructureConfiguration {
}
