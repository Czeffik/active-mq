package com.trzewik.activemq.interfaces.jms;

import com.trzewik.activemq.interfaces.jms.information.InformationConsumerConfiguration;
import com.trzewik.activemq.interfaces.jms.translation.TranslationConsumerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    InformationConsumerConfiguration.class,
    TranslationConsumerConfiguration.class
})
@Configuration
public class JmsConfiguration {
}
