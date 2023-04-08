package com.meightsoft.crudexample.validator;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantValidator {

    private final RestaurantRepository restaurantRepository;

    public void validateOnGet(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantValidator::validateOnGet [restaurantId={}]", restaurantId);

        validateIfExists(restaurantId);
    }

    public void validateOnUpdate(Restaurant restaurant) throws EntityNotFoundException {
        log.debug("RestaurantValidator::validateOnUpdate [restaurant={}]", restaurant);

        validateIfExists(restaurant.getId());
    }

    public void validateOnDelete(Long restaurantId) throws EntityNotFoundException {
        log.debug("RestaurantValidator::validateOnDelete [restaurantId={}]", restaurantId);

        validateIfExists(restaurantId);
    }

    private void validateIfExists(Long restaurantId) throws EntityNotFoundException {
        log.trace("RestaurantValidator::validateIfExist [restaurantId={}]", restaurantId);

        if (restaurantId == null || !restaurantRepository.existsById(restaurantId)) {
            throw new EntityNotFoundException("Restaurant not found");
        }
    }
}
