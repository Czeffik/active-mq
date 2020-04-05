package com.trzewik.activemq.interfaces;

import com.trzewik.activemq.interfaces.jms.JmsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    JmsConfiguration.class
})
@Configuration
public class InterfacesConfiguration {
}
