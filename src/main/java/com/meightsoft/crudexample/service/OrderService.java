package com.meightsoft.crudexample.service;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order get(String orderId) throws EntityNotFoundException;

    List<Order> list();

    List<Order> listByRestaurantId(String restaurantId);

    Order update(Order order) throws EntityNotFoundException;

    Order updateStatus(String orderId) throws EntityNotFoundException;

    void delete(String orderId) throws EntityNotFoundException;

    void deleteAllByRestaurantId(String restaurantId);

    interface RestaurantService {
    }
}
