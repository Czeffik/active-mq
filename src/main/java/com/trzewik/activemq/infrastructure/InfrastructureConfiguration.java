package com.trzewik.activemq.infrastructure;

import com.trzewik.activemq.infrastructure.db.DbConfiguration;
import com.trzewik.activemq.infrastructure.grpc.GrpcInfrastructureConfiguration;
import com.trzewik.activemq.infrastructure.jms.JmsInfrastructureConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    JmsInfrastructureConfiguration.class,
    DbConfiguration.class,
    GrpcInfrastructureConfiguration.class
})
@Configuration
public class InfrastructureConfiguration {
}
