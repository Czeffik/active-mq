package com.trzewik.activemq.infrastructure.db.information

import com.trzewik.activemq.domain.information.Information
import com.trzewik.activemq.domain.information.InformationRepository
import com.trzewik.activemq.infrastructure.db.DbConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ActiveProfiles(['test'])
@ContextConfiguration(
    classes = [DbConfiguration]
)
class InformationRepositoryInMemoryIT extends Specification {
    @Autowired
    InformationRepository informationRepository


    def 'should get information from repository'(){
        given:
            String id = 'test id'
        when:
            Information information = informationRepository.getById(id)
        then:
            information.message == "message for: [${id}]"
    }
}
