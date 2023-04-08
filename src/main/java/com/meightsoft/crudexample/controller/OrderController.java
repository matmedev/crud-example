package com.meightsoft.crudexample.controller;

import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.facade.RestaurantOrderFacade;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders")
public class OrderController {

    private final OrderService orderService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@PathVariable Long restaurantId, @RequestBody @Valid Order order) {
        log.debug("OrderController::create [restaurantId={}, order={}]", restaurantId, order);

        order.setStatus(OrderStatus.PENDING);

        try {
            return restaurantOrderFacade.createOrder(order, restaurantId);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @GetMapping(path = "/{orderId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order get(@PathVariable Long restaurantId, @PathVariable Long orderId) {
        log.debug("OrderController::get [restaurantId={}, orderId={}]", restaurantId, orderId);

        try {
            return orderService.get(orderId);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> list(@PathVariable Long restaurantId) {
        log.debug("OrderController::list [restaurantId={}]", restaurantId);

        try {
            return orderService.listByRestaurantId(restaurantId);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @PutMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order update(@PathVariable Long restaurantId, @PathVariable Long orderId, @RequestBody @Valid Order order) {
        log.debug("OrderController::update [restaurantId={}, orderId={}, order={}]", restaurantId, orderId, order);

        order.setId(orderId);

        try {
            return orderService.update(order);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @DeleteMapping(path = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long orderId) {
        log.debug("OrderController::delete [restaurantId={}, orderId={}]", restaurantId, orderId);

        try {
            orderService.delete(orderId);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }
}
