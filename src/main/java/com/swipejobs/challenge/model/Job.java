package com.swipejobs.challenge.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swipejobs.challenge.util.BillRateSerializer;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Job document in the Job collection
 */
@MappedEntity
@Getter
@Setter
@NoArgsConstructor
@ReflectiveAccess
public class Job {
    @Id
    @GeneratedValue
    private String id;

    private Integer jobId;
    private String guid;
    private String company;
    private String jobTitle;

    @JsonDeserialize(using = BillRateSerializer.class)
    private Double billRate;

    private Location location;
    private List<String> requiredCertificates = new ArrayList<>();
    private boolean driverLicenseRequired;
}
