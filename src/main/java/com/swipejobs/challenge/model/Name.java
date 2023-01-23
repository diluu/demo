package com.swipejobs.challenge.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the name of a worker
 */
@Getter
@Setter
@Serdeable
public class Name {

    private String first;
    private String last;
}
