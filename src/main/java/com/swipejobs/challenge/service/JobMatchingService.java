package com.swipejobs.challenge.service;

import com.swipejobs.challenge.dto.MatchingJobDto;
import com.swipejobs.challenge.model.Job;
import com.swipejobs.challenge.model.JobSearchAddress;
import com.swipejobs.challenge.model.Worker;
import com.swipejobs.challenge.repository.JobRepository;
import com.swipejobs.challenge.repository.WorkerRepository;
import com.swipejobs.challenge.util.MatchingConfiguration;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Service for Job matching operations
 */
@Singleton
@Slf4j
public class JobMatchingService {

    private final JobRepository jobRepository;
    private final WorkerRepository workerRepository;
    private final MatchingConfiguration matchingConfiguration;

    public JobMatchingService(JobRepository jobRepository, WorkerRepository workerRepository, MatchingConfiguration matchingConfiguration) {
        this.jobRepository = jobRepository;
        this.workerRepository = workerRepository;
        this.matchingConfiguration = matchingConfiguration;
    }

    /**
     * Find a pre-configured number of matching jobs for a given worker
     *
     * @param workerId The worker id
     * @return the matching jobs
     */
    public List<MatchingJobDto> matchJobsForWorker(Integer workerId) {
        Worker worker = workerRepository.findByUserId(workerId).orElseThrow();
        List<Job> allJobs = worker.isHasDriversLicense() ?
                StreamSupport.stream(jobRepository.findAll().spliterator(), false).toList() :
                jobRepository.findByDriverLicenseRequired(false);
        return allJobs.stream()
                .filter(j -> j.getRequiredCertificates().isEmpty() || worker.getCertificates().containsAll(j.getRequiredCertificates()))
                .map(j -> MatchingJobDto.builder()
                        .jobId(j.getJobId())
                        .guid(j.getGuid())
                        .company(j.getCompany())
                        .jobTitle(j.getJobTitle())
                        .billRate(j.getBillRate())
                        .distance(calculateDistanceBetweenLocations(j.getLocation().getLatitude(), j.getLocation().getLongitude(), worker.getJobSearchAddress()))
                        .build())
                .filter(jdto -> jdto.getDistance() <= worker.getJobSearchAddress().getMaxJobDistance())
                .sorted(Comparator.comparing(MatchingJobDto::getBillRate).reversed())
                .limit(matchingConfiguration.getMatchCount())
                .toList();
    }

    /**
     * Helper function to calculate distance between two locations
     * Referenced from https://www.w3resource.com/java-exercises/basic/java-basic-exercise-36.php
     *
     * @param lat1    latitude of the job location
     * @param lon1    longitude of job location
     * @param address the job search address of the worker
     * @return the calculated distance in Kilometers
     */
    private double calculateDistanceBetweenLocations(double lat1, double lon1, JobSearchAddress address) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        double lat2 = Math.toRadians(address.getLatitude());
        double lon2 = Math.toRadians(address.getLongitude());

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
