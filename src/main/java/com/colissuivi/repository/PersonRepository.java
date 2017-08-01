package com.colissuivi.repository;

import com.colissuivi.domain.Person;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends MongoRepository<Person,String> {
    
}
