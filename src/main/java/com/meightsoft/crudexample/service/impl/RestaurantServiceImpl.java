package com.meightsoft.crudexample.service.impl;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import com.meightsoft.crudexample.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;

    @Override
    public Restaurant save(Restaurant restaurant) {
        log.debug("RestaurantService::create [restaurant={}]", restaurant);

        var restaurantDocument = restaurantMapper.toDocument(restaurant);
        restaurantDocument = restaurantRepository.save(restaurantDocument);

        return restaurantMapper.toModel(restaurantDocument);

    }

    @Override
    public boolean exists(String restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @Override
    public Restaurant get(String restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::get [restaurantId={}]", restaurantId);

        var optionalRestaurantEntity = restaurantRepository.findById(restaurantId);
        var restaurantDocument = optionalRestaurantEntity.orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        return restaurantMapper.toModel(restaurantDocument);
    }

    @Override
    public List<Restaurant> list() {
        log.debug("RestaurantService::list");

        var restaurants = restaurantRepository.findAll();

        return restaurants.stream().map(restaurantMapper::toModel).toList();
    }

    @Override
    public void delete(String restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantService::delete [restaurantId={}]", restaurantId);

        if (restaurantRepository.existsById(restaurantId)) {
            restaurantRepository.deleteById(restaurantId);
        } else {
            throw new EntityNotFoundException("Restaurant not found");
        }
    }
}
