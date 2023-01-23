package com.swipejobs.challenge.model;

import com.swipejobs.challenge.enums.DistanceUnit;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent the address of a worker
 */
@Getter
@Setter
@Serdeable
public class JobSearchAddress {
    private DistanceUnit unit;
    private Integer maxJobDistance;
    private Double longitude;
    private Double latitude;
}
