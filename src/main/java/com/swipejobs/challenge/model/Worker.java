package com.swipejobs.challenge.model;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Worker document in the Worker collection
 */
@MappedEntity
@Getter
@Setter
@NoArgsConstructor
public class Worker {
    @Id
    @GeneratedValue
    private String id;

    private Integer userId;
    private String guid;
    private Name name;
    private boolean hasDriversLicense;
    private JobSearchAddress jobSearchAddress;
    private List<String> certificates = new ArrayList<>();
}
