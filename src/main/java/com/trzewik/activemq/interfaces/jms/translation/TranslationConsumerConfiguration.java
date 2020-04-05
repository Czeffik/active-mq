package com.trzewik.activemq.interfaces.jms.translation;

import com.trzewik.activemq.interfaces.jms.common.CommonConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

@Import({
    CommonConsumerConfiguration.class
})
@EnableJms
@Configuration
public class TranslationConsumerConfiguration {
    @Bean
    TranslationConsumer translationConsumer() {
        return new TranslationConsumer();
    }
}
