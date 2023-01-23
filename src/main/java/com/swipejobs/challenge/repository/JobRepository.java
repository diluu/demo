package com.swipejobs.challenge.repository;

import com.swipejobs.challenge.model.Job;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Mongo repository to operate on Job collection
 */
@MongoRepository
public interface JobRepository extends CrudRepository<Job, String> {

    Optional<Job> findByJobId(Integer jobId);

    List<Job> findByDriverLicenseRequired(Boolean driverLicenseRequired);
}
