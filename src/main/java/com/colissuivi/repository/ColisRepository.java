package com.colissuivi.repository;

import com.colissuivi.domain.Colis;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Colis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColisRepository extends MongoRepository<Colis,String> {
    
}
