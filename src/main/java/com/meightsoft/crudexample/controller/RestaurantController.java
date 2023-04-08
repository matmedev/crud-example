package com.meightsoft.crudexample.controller;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.facade.RestaurantOrderFacade;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.service.RestaurantService;
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
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        log.debug("RestaurantController::create [restaurant={}]", restaurant);

        try {
            return restaurantService.create(restaurant);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @GetMapping(path = "/{restaurantId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Restaurant get(@PathVariable Long restaurantId) {
        log.debug("RestaurantController::get [restaurantId={}]", restaurantId);

        try {
            return restaurantService.get(restaurantId);
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
    public List<Restaurant> list() {
        log.debug("RestaurantController::list");

        try {
            return restaurantService.list();
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @PutMapping(path = "/{restaurantId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        log.debug("RestaurantController::update [restaurantId={}, restaurant={}]", restaurantId, restaurant);

        restaurant.setId(restaurantId);

        try {
            return restaurantService.update(restaurant);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }

    @DeleteMapping(path = "/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long restaurantId) {
        log.debug("RestaurantController::delete [restaurantId={}]", restaurantId);

        try {
            restaurantOrderFacade.deleteRestaurant(restaurantId);
        } catch (EntityNotFoundException e) {
            log.error("The specified item doesn't exist", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred!", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error!", e);
        }
    }
}
