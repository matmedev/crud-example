package com.meightsoft.crudexample.controller;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.facade.RestaurantOrderFacade;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Order.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid order data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@PathVariable String restaurantId, @Valid @RequestBody Order order) {
        log.debug("OrderController::create [restaurantId={}, order={}]", restaurantId, order);

        try {
            return restaurantOrderFacade.createOrder(order, restaurantId);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @GetMapping(path = "/{orderId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order get(@PathVariable String restaurantId, @PathVariable String orderId) {
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
    public List<Order> list(@PathVariable String restaurantId) {
        log.debug("OrderController::list [restaurantId={}]", restaurantId);

        try {
            return orderService.listByRestaurantId(restaurantId);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @PutMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order update(@PathVariable String restaurantId, @PathVariable String orderId, @RequestBody @Valid Order order) {
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
    public void delete(@PathVariable String restaurantId, @PathVariable String orderId) {
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

    @PostMapping(path = "/{orderId}/next-status")
    @ResponseStatus(HttpStatus.OK)
    public Order updateStatus(@PathVariable String restaurantId, @PathVariable String orderId) {
        log.debug("OrderStatusController::update [restaurantId={}, orderId={}]", restaurantId, orderId);

        try {
            return orderService.updateStatus(orderId);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }
}
