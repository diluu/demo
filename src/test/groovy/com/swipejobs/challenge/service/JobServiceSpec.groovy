package com.swipejobs.challenge.service

import com.swipejobs.challenge.repository.JobRepository
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

/**
 * Tests for {@Link JobService}
 */
@MicronautTest
class JobServiceSpec extends Specification {

    @Inject
    JobService jobService

    @Inject
    JobRepository jobRepository

    def "jobs are bootstrapped correctly"() {
        given:
        jobRepository.deleteAll()

        when:
        jobService.bootstrapJobs()

        then:
        jobRepository.findAll().size() == 40
    }
}
