package com.meightsoft.crudexample.persistence.repository;

import com.meightsoft.crudexample.persistence.model.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {

    List<OrderDocument> findAllByRestaurantId(String restaurantId);

    void deleteAllByRestaurantId(String restaurantId);
}
