package com.swipejobs.challenge.controller

import com.swipejobs.challenge.service.JobMatchingService
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.validation.ConstraintViolationException

@MicronautTest
class JobMatchingControllerSpec extends Specification {
    @Inject
    JobMatchingController controller

    @Inject
    JobMatchingService jobMatchingServiceMock

    @MockBean(JobMatchingService)
    JobMatchingService jobMatchingServiceMock() {
        Mock(JobMatchingService)
    }

    void "Job matching calls direct to the correct service"() {
        when: "match is called"
        controller.match(workerId)

        then: "the correct method is called"
        1 * jobMatchingServiceMock.matchJobsForWorker(workerId)

        where:
        input              | workerId
        "positive integer" | 1
        "zero"             | 0
    }


    void "Constraint violation is thrown on invalid worker ids"() {
        when:
        controller.match(-1)

        then:
        thrown(ConstraintViolationException)
    }
}
