package com.swipejobs.challenge.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipejobs.challenge.model.Worker;
import com.swipejobs.challenge.repository.WorkerRepository;
import com.swipejobs.challenge.util.MatchingConfiguration;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Service for operations on Worker collection
 */
@Singleton
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final MatchingConfiguration matchingConfiguration;

    public WorkerService(WorkerRepository workerRepository, MatchingConfiguration matchingConfiguration) {
        this.workerRepository = workerRepository;
        this.matchingConfiguration = matchingConfiguration;
    }

    /**
     * Bootstrap workers using a pre-configured source data url
     *
     * @throws IOException if it is failed to read data from the source data url
     */
    public void bootstrapWorkers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES);
        JsonNode workerNode = objectMapper.readTree(new URL(matchingConfiguration.getWorkersUrl()));
        List<Worker> workers = Arrays.asList(objectMapper.treeToValue(workerNode, Worker[].class));
        workers.forEach(w -> {
            if (workerRepository.findByUserId(w.getUserId()).isEmpty()) {
                workerRepository.save(w);
            }
        });
    }
}
