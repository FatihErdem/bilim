package com.decimatech.bilim.repository;

import com.decimatech.bilim.model.Beacon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BeaconRepository extends MongoRepository<Beacon, Long> {



}
