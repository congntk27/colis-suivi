package com.colissuivi.repository;

import com.colissuivi.domain.Agence;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends MongoRepository<Agence,String> {
    
}
