package com.trzewik.activemq

import com.trzewik.activemq.interfaces.grpc.information.InformationControllerGrpc
import com.trzewik.activemq.interfaces.grpc.information.InformationStreamControllerGrpc
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy

@TestConfiguration
class TestGrpcConfig {
    @Lazy
    @Bean
    InformationControllerGrpc.InformationControllerBlockingStub informationControllerBlockingStub(Channel testChannel) {
        return InformationControllerGrpc.newBlockingStub(testChannel)
    }

    @Lazy
    @Bean
    InformationStreamControllerGrpc.InformationStreamControllerStub informationStreamControllerStub(Channel testChannel) {
        return InformationStreamControllerGrpc.newStub(testChannel)
    }

    @Lazy
    @Bean
    Channel testChannel(GRpcServerProperties grpcServerProperties) {
        return ManagedChannelBuilder
            .forTarget("localhost:${grpcServerProperties.runningPort}")
            .usePlaintext()
            .build()
    }
}
