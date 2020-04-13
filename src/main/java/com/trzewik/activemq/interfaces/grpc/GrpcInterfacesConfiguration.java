package com.trzewik.activemq.interfaces.grpc;

import com.trzewik.activemq.interfaces.grpc.information.InformationControllerConfiguration;
import com.trzewik.activemq.interfaces.grpc.information.InformationStreamControllerConfiguration;
import org.lognet.springboot.grpc.autoconfigure.GRpcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    InformationControllerConfiguration.class,
    InformationStreamControllerConfiguration.class,
    GRpcAutoConfiguration.class
})
@Configuration
public class GrpcInterfacesConfiguration {

}
