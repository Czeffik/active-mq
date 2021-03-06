package com.trzewik.activemq;

import com.trzewik.activemq.infrastructure.DomainConfiguration;
import com.trzewik.activemq.infrastructure.InfrastructureConfiguration;
import com.trzewik.activemq.interfaces.InterfacesConfiguration;
import org.lognet.springboot.grpc.autoconfigure.GRpcAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import(
    {
        DomainConfiguration.class,
        InfrastructureConfiguration.class,
        InterfacesConfiguration.class
    }
)
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {ActiveMQAutoConfiguration.class, GRpcAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
