package com.decimatech.bilim.repository;

import com.decimatech.bilim.model.Gallery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends MongoRepository<Gallery, Long> {

}
