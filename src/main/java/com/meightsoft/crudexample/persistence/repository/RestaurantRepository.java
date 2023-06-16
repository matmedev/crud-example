package com.meightsoft.crudexample.persistence.repository;

import com.meightsoft.crudexample.persistence.model.RestaurantDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<RestaurantDocument, String> {
}
