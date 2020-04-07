package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import com.trzewik.activemq.infrastructure.jms.common.CommonProducerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

@Import({
    CommonProducerConfiguration.class
})
@Configuration
public class JmsInformationProducerConfiguration {

    @Bean
    InformationProducer informationProducer(
        JmsTemplate jmsTemplate,
        @Value("${jms.queue.information}") String queue,
        @Value("${jms.topic.virtual.information}") String virtualTopic
    ) {
        return new JmsInformationProducer(jmsTemplate, Arrays.asList(queue, virtualTopic));
    }
}
