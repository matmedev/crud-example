package com.meightsoft.crudexample.service;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.mapstruct.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import com.meightsoft.crudexample.validator.RestaurantValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    // TODO remove validator (use orElseThrow)
    private final RestaurantValidator restaurantValidator;

    public Restaurant create(Restaurant restaurant) {
        log.debug("RestaurantService::create [restaurant={}]", restaurant);

        var restaurantEntity = restaurantMapper.toEntity(restaurant);
        restaurantEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.toModel(restaurantEntity);
    }

    public Restaurant get(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::get [restaurantId={}]", restaurantId);

        restaurantValidator.validateOnGet(restaurantId);

        var optionalRestaurant = restaurantRepository.findById(restaurantId);

        return restaurantMapper.toModel(optionalRestaurant.get());
    }

    public List<Restaurant> list() {
        log.debug("RestaurantService::list");

        var restaurants = restaurantRepository.findAll();

        return StreamSupport.stream(restaurants.spliterator(), false)
                .map(restaurantMapper::toModel)
                .toList();
    }

    public Restaurant update(Restaurant restaurant) throws EntityNotFoundException {
        log.debug("RestaurantService::update [restaurant={}]", restaurant);

        restaurantValidator.validateOnUpdate(restaurant);

        var restaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(restaurant));

        return restaurantMapper.toModel(restaurantEntity);
    }

    public void delete(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::delete [restaurantId={}]", restaurantId);

        restaurantValidator.validateOnDelete(restaurantId);

        restaurantRepository.deleteById(restaurantId);
    }
}
