package com.trzewik.activemq.interfaces;

import com.trzewik.activemq.interfaces.grpc.GrpcConfiguration;
import com.trzewik.activemq.interfaces.jms.JmsInterfacesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    JmsInterfacesConfiguration.class,
    GrpcConfiguration.class
})
@Configuration
public class InterfacesConfiguration {
}
