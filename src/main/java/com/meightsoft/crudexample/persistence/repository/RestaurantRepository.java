package com.meightsoft.crudexample.persistence.repository;

import com.meightsoft.crudexample.persistence.model.RestaurantEntity;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {

}
