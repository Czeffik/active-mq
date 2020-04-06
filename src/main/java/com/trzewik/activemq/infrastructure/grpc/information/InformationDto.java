package com.trzewik.activemq.infrastructure.grpc.information;

import com.trzewik.activemq.domain.information.Information;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationDto {
    public static InformationDTO from(Information information) {
        return from(information.getMessage());
    }

    public static InformationDTO from(String message) {
        return InformationDTO.newBuilder()
            .setMessage(message)
            .build();
    }
}
