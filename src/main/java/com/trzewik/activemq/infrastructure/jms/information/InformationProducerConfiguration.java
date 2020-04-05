package com.trzewik.activemq.infrastructure.jms.information;

import com.trzewik.activemq.domain.information.InformationProducer;
import com.trzewik.activemq.infrastructure.jms.common.CommonProducerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;

@Import({
    CommonProducerConfiguration.class
})
@Configuration
public class InformationProducerConfiguration {

    @Bean
    InformationProducer informationProducer(
        JmsTemplate jmsTemplate,
        @Value("${jms.queue.information}") String queue,
        @Value("${jms.topic.information}") String topic,
        @Value("${jms.topic.virtual.information}") String virtualTopic
    ) {
        return new InformationProducerImpl(jmsTemplate, queue, topic, virtualTopic);
    }
}
