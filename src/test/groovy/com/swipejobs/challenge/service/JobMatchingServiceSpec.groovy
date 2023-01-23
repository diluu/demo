package com.swipejobs.challenge.service

import com.swipejobs.challenge.dto.MatchingJobDto
import com.swipejobs.challenge.enums.DistanceUnit
import com.swipejobs.challenge.model.Job
import com.swipejobs.challenge.model.JobSearchAddress
import com.swipejobs.challenge.model.Location
import com.swipejobs.challenge.model.Worker
import com.swipejobs.challenge.repository.JobRepository
import com.swipejobs.challenge.repository.WorkerRepository
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

/**
 * Tests for {@Link JobMatchingService}
 */
@MicronautTest
class JobMatchingServiceSpec extends Specification {

    @Inject
    JobMatchingService jobMatchingService

    @Inject
    JobRepository jobRepository

    @Inject
    WorkerRepository workerRepository

    Job job1
    Job job2
    Job job3
    Job job4
    Job job5

    void setup() {
        jobRepository.deleteAll()
        workerRepository.deleteAll()
        job1 = buildJob(1, "Company1", "job role1", 10.12, new Location(longitude: 13.864602, latitude: 49.93359), ["Certificate 1", "Certificate 2"], false)
        job2 = buildJob(2, "Company2", "job role2", 15.12, new Location(longitude: 13.810606, latitude: 50.091271), ["Certificate 1"], true)
        job3 = buildJob(3, "Company3", "job role3", 25.12, new Location(longitude: 13.603433, latitude: 50.021556), [], true)
        job4 = buildJob(4, "Company4", "job role4", 35.12, new Location(longitude: 13.460515, latitude: 49.980683), ["Certificate 1", "Certificate 2"], false)
        job5 = buildJob(5, "Company5", "job role5", 45.12, new Location(longitude: 15.220885, latitude: 51.176094), [], false)
    }

    def "matchJobsForWorker returns correct results for a worker with drivers licence within the job search distance"() {
        given:
        Worker worker = buildWorker(1, true, new JobSearchAddress(unit: DistanceUnit.KM, maxJobDistance: 80, longitude: 14.377703d, latitude: 50.270476d), ["Certificate 1", "Certificate 2"])

        when:
        List<MatchingJobDto> jobs = jobMatchingService.matchJobsForWorker(worker.userId)

        then:
        jobs.size() == 3
        jobs[0].jobId == job4.jobId
        jobs[1].jobId == job3.jobId
        jobs[2].jobId == job2.jobId
    }

    def "matchJobsForWorker returns correct results for a worker without drivers licence within the job search distance"() {
        given:
        Worker worker = buildWorker(2, false, new JobSearchAddress(unit: DistanceUnit.KM, maxJobDistance: 80, longitude: 14.377703d, latitude: 50.270476d), ["Certificate 1", "Certificate 2"])

        when:
        List<MatchingJobDto> jobs = jobMatchingService.matchJobsForWorker(worker.userId)

        then:
        jobs.size() == 2
        jobs[0].jobId == job4.jobId
        jobs[1].jobId == job1.jobId
    }

    def "matchJobsForWorker matches certificates correctly when worker has one certificate only"() {
        given:
        Worker worker = buildWorker(3, true, new JobSearchAddress(unit: DistanceUnit.KM, maxJobDistance: 80, longitude: 14.377703d, latitude: 50.270476d), ["Certificate 1"])

        when:
        List<MatchingJobDto> jobs = jobMatchingService.matchJobsForWorker(worker.userId)

        then:
        jobs.size() == 2
        jobs[0].jobId == job3.jobId
        jobs[1].jobId == job2.jobId
    }

    def "matchJobsForWorker matches certificates correctly when worker has no certificates"() {
        given:
        Worker worker = buildWorker(4, true, new JobSearchAddress(unit: DistanceUnit.KM, maxJobDistance: 80, longitude: 14.377703d, latitude: 50.270476d), [])

        when:
        List<MatchingJobDto> jobs = jobMatchingService.matchJobsForWorker(worker.userId)

        then:
        jobs.size() == 1
        jobs[0].jobId == job3.jobId
    }

    def "matchJobsForWorker returns an empty list if no matching results found"() {
        given:
        Worker worker = buildWorker(5, false, new JobSearchAddress(unit: DistanceUnit.KM, maxJobDistance: 80, longitude: 14.377703d, latitude: 50.270476d), [])

        when:
        List<MatchingJobDto> jobs = jobMatchingService.matchJobsForWorker(worker.userId)

        then:
        jobs.size() == 0
    }

    private Job buildJob(Integer jobId, String company, String jobTitle, Double billRate, Location location, List<String> requiredCertificates, Boolean driverLicenseRequired) {
        return jobRepository.save(new Job(jobId: jobId, guid: UUID.randomUUID().toString(), company: company, jobTitle: jobTitle, billRate: billRate, location: location, requiredCertificates: requiredCertificates, driverLicenseRequired: driverLicenseRequired))
    }

    private Worker buildWorker(Integer workerId, Boolean hasDriversLicense, JobSearchAddress jobSearchAddress, List<String> certificates) {
        return workerRepository.save(new Worker(userId: workerId, guid: UUID.randomUUID().toString(), hasDriversLicense: hasDriversLicense, jobSearchAddress: jobSearchAddress, certificates: certificates))
    }

}
