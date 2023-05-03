package com.meightsoft.crudexample.unit;

import com.meightsoft.crudexample.fixtures.RestaurantFixtures;
import com.meightsoft.crudexample.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RestaurantMapperTest {

    private final RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);

    @Test
    public void toEntityShouldReturnRestaurantEntity() {
        // Given
        var restaurant = RestaurantFixtures.getSingleRestaurant(true);

        // When
        var result = restaurantMapper.toEntity(restaurant);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(RestaurantFixtures.getSingleRestaurantEntity(true));
    }

    @Test
    public void toModelShouldReturnRestaurant() {
        // Given
        var restaurantEntity = RestaurantFixtures.getSingleRestaurantEntity(true);

        // When
        var result = restaurantMapper.toModel(restaurantEntity);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(RestaurantFixtures.getSingleRestaurant(true));
    }
}
