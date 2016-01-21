package com.decimatech.bilim.repository;

import com.decimatech.bilim.model.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StationRepository extends MongoRepository<Station, Long> {

}
