package com.swipejobs.challenge.util;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

/**
 * Configuration class for source data urls
 */
@Data
@ConfigurationProperties("matching")
public class MatchingConfiguration {

    private String workersUrl;
    private String jobsUrl;
    private Integer matchCount;
}
