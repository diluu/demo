package com.swipejobs.challenge.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO class representing the response sent back when matching jobs
 */
@Builder
@Getter
public class MatchingJobDto {
    private Integer jobId;
    private String guid;
    private String company;
    private String jobTitle;
    private Double billRate;
    private Double distance;
}
