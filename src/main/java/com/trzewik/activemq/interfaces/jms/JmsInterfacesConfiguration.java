package com.trzewik.activemq.interfaces.jms;

import com.trzewik.activemq.interfaces.jms.information.InformationConsumerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    InformationConsumerConfiguration.class
})
@Configuration
public class JmsInterfacesConfiguration {
}
