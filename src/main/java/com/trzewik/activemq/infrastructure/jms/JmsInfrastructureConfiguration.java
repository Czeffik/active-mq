package com.trzewik.activemq.infrastructure.jms;

import com.trzewik.activemq.infrastructure.jms.information.InformationProducerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    InformationProducerConfiguration.class
})
@Configuration
public class JmsInfrastructureConfiguration {
}
