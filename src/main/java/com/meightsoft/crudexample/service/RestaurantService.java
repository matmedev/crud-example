package com.meightsoft.crudexample.service;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.mapstruct.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public Restaurant save(Restaurant restaurant) {
        log.debug("RestaurantService::create [restaurant={}]", restaurant);

        var restaurantEntity = restaurantMapper.toEntity(restaurant);
        restaurantEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.toModel(restaurantEntity);
    }

    public Restaurant get(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::get [restaurantId={}]", restaurantId);

        var optionalRestaurantEntity = restaurantRepository.findById(restaurantId);
        var restaurantEntity = optionalRestaurantEntity.orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        return restaurantMapper.toModel(restaurantEntity);
    }

    public List<Restaurant> list() {
        log.debug("RestaurantService::list");

        var restaurants = restaurantRepository.findAll();

        return StreamSupport.stream(restaurants.spliterator(), false)
                .map(restaurantMapper::toModel)
                .toList();
    }

    public void delete(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::delete [restaurantId={}]", restaurantId);

        try {
            restaurantRepository.deleteById(restaurantId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Restaurant not found");
        }
    }
}
