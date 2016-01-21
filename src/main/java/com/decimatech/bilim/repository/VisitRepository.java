package com.decimatech.bilim.repository;

import com.decimatech.bilim.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends MongoRepository<Visit, Long> {

}
