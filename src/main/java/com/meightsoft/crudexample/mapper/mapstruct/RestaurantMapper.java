package com.meightsoft.crudexample.mapper.mapstruct;

import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.model.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toModel(RestaurantEntity restaurant);
}
