package com.meightsoft.crudexample.controller;

import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders/{orderId}/status")
public class OrderStatusController {

    private final OrderService orderService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order update(@PathVariable Long restaurantId, @PathVariable Long orderId, @RequestParam OrderStatus status) {
        log.debug("OrderStatusController::update [restaurantId={}, orderId={}]", restaurantId, orderId);

        try {
            return orderService.updateStatus(orderId, status);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }
}
