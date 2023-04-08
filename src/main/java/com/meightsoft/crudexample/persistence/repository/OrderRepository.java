package com.meightsoft.crudexample.persistence.repository;

import com.meightsoft.crudexample.persistence.model.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Iterable<OrderEntity> findAllByRestaurantId(Long restaurantId);

    void deleteAllByRestaurantId(Long restaurantId);
}
