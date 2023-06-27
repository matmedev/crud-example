package com.meightsoft.crudexample.service;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    boolean exists(String restaurantId);

    Restaurant get(String restaurantId) throws EntityNotFoundException;

    List<Restaurant> list();

    void delete(String restaurantId) throws EntityNotFoundException;
}
