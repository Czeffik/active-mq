package com.trzewik.activemq.infrastructure.db.information;

import com.trzewik.activemq.domain.information.Information;
import com.trzewik.activemq.domain.information.InformationRepository;

public class InformationRepositoryInMemoryImpl implements InformationRepository {

    private static String merge(String str1, String str2) {
        return String.format("%s for: [%s]", str1, str2);
    }

    @Override
    public Information getById(String id) {
        return new Information(id, merge("message", id));
    }
}
