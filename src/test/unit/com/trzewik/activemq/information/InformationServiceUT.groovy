package com.trzewik.activemq.information

import com.trzewik.activemq.domain.information.Information
import com.trzewik.activemq.domain.information.InformationProducer
import com.trzewik.activemq.domain.information.InformationRepository
import com.trzewik.activemq.domain.information.InformationService
import com.trzewik.activemq.domain.information.InformationServiceFactory
import spock.lang.Specification
import spock.lang.Subject

class InformationServiceUT extends Specification {
    InformationProducer firstProducer = Mock()
    InformationProducer secondProducer = Mock()
    List<InformationProducer> producers = [firstProducer, secondProducer]
    InformationRepository repository = Mock()
    @Subject
    InformationService service

    def setup() {
        service = InformationServiceFactory.create(producers, repository)
    }

    def 'should notify all producers'() {
        given:
            String message = 'test message'
        when:
            service.notifyAll(message)
        then:
            1 * firstProducer.send("Original: [${message}] with modification: [MODIFICATION]")
            1 * secondProducer.send("Original: [${message}] with modification: [MODIFICATION]")
    }

    def 'should get information from repository by id'() {
        given:
            String id = 'test id'
        and:
            Information expectedInformation = new Information(id, 'example message')
        when:
            Information information = service.getInformation(id)
        then:
            1 * repository.getById(id) >> expectedInformation
        and:
            information == expectedInformation
    }
}
