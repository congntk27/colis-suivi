package com.colissuivi.repository;

import com.colissuivi.domain.Cheminement;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cheminement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheminementRepository extends MongoRepository<Cheminement,String> {
    
}
