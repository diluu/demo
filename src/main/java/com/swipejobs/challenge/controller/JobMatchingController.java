package com.swipejobs.challenge.controller;

import com.swipejobs.challenge.dto.MatchingJobDto;
import com.swipejobs.challenge.service.JobMatchingService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Controller for job matching operations
 */
@Controller("/api/match")
@Slf4j
public class JobMatchingController {

    private final JobMatchingService jobMatchingService;

    public JobMatchingController(JobMatchingService jobMatchingService) {
        this.jobMatchingService = jobMatchingService;
    }

    /**
     * Accepts a valid worker id and returns a pre-configured number of matching jobs for that worker
     *
     * @param workerId The worker id
     * @return the matching jobs
     */
    @Get("/{workerId}")
    List<MatchingJobDto> match(@PositiveOrZero Integer workerId) {
        log.debug("match called for worker {}", workerId);
        return jobMatchingService.matchJobsForWorker(workerId);
    }
}
