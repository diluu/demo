package com.swipejobs.challenge.repository;

import com.swipejobs.challenge.model.Worker;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Mongo repository to operate on Worker collection
 */
@MongoRepository
public interface WorkerRepository extends CrudRepository<Worker, String> {

    Optional<Worker> findByUserId(Integer workerId);
}
