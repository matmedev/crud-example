package com.meightsoft.crudexample.facade;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.service.OrderService;
import com.meightsoft.crudexample.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantOrderFacade {

    private final RestaurantService restaurantService;
    private final OrderService orderService;

    public Order createOrder(Order order, String restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantOrderFacade::createOrder [order={}, restaurantId={}]", order, restaurantId);

        var restaurant = restaurantService.get(restaurantId);
        order.setRestaurant(restaurant);

        return orderService.create(order);
    }

    public void deleteRestaurant(String restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantOrderFacade::deleteRestaurant [restaurantId={}]", restaurantId);

        restaurantService.delete(restaurantId);
        orderService.deleteAllByRestaurantId(restaurantId);
    }
}
