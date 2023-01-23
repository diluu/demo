package com.swipejobs.challenge.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipejobs.challenge.model.Job;
import com.swipejobs.challenge.repository.JobRepository;
import com.swipejobs.challenge.util.MatchingConfiguration;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Service for operations on Job collection
 */
@Singleton
@Slf4j
public class JobService {

    private final JobRepository jobRepository;
    private final MatchingConfiguration matchingConfiguration;

    public JobService(JobRepository jobRepository, MatchingConfiguration matchingConfiguration) {
        this.jobRepository = jobRepository;
        this.matchingConfiguration = matchingConfiguration;
    }

    /**
     * Bootstrap jobs using a pre-configured source data url
     *
     * @throws IOException if it is failed to read data from the source data url
     */
    public void bootstrapJobs() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES);
        JsonNode jobsNode = objectMapper.readTree(new URL(matchingConfiguration.getJobsUrl()));
        List<Job> jobs = Arrays.asList(objectMapper.treeToValue(jobsNode, Job[].class));
        jobs.forEach(j -> {
            if (jobRepository.findByJobId(j.getJobId()).isEmpty()) {
                jobRepository.save(j);
            }
        });
    }
}
