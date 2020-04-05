package com.trzewik.activemq.infrastructure;

import com.trzewik.activemq.infrastructure.jms.JmsInfrastructureConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    JmsInfrastructureConfiguration.class
})
@Configuration
public class InfrastructureConfiguration {
}
