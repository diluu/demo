package com.swipejobs.challenge;

import com.swipejobs.challenge.service.JobService;
import com.swipejobs.challenge.service.WorkerService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Boostrap the data into the database in none-test environments when the application starts up
 */
@Singleton
@Requires(notEnv = Environment.TEST)
@Slf4j
public class Bootstrap implements ApplicationEventListener<ServiceReadyEvent> {

    private final WorkerService workerService;
    private final JobService jobService;

    public Bootstrap(WorkerService workerService, JobService jobService) {
        this.workerService = workerService;
        this.jobService = jobService;
    }

    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        log.info("Bootstrapping source data");
        try {
            workerService.bootstrapWorkers();
            log.info("Workers bootstrapped");
        } catch (IOException e) {
            log.error("Error bootstrapping workers", e);
        }
        try {
            jobService.bootstrapJobs();
            log.info("Jobs bootstrapped");
        } catch (IOException e) {
            log.error("Error bootstrapping jobs", e);
        }
    }
}
