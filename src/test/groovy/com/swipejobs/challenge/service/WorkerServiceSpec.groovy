package com.swipejobs.challenge.service


import com.swipejobs.challenge.repository.WorkerRepository
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

/**
 * Tests for {@Link WorkerService}
 */
@MicronautTest
class WorkerServiceSpec extends Specification {

    @Inject
    WorkerService workerService

    @Inject
    WorkerRepository workerRepository

    def "workers are bootstrapped correctly"() {
        given:
        workerRepository.deleteAll()

        when:
        workerService.bootstrapWorkers()

        then:
        workerRepository.findAll().size() == 50
    }
}
