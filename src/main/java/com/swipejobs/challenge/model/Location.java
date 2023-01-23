package com.swipejobs.challenge.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the location of a job
 */
@Getter
@Setter
@Serdeable
public class Location {
    private Double longitude;
    private Double latitude;
}
