package com.meightsoft.crudexample.mapper;

import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.model.RestaurantDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantWithoutOrdersMapper {

    @Mapping(target = "orders", ignore = true)
    RestaurantDocument toDocument(Restaurant restaurant);

    @Mapping(target = "orders", ignore = true)
    Restaurant toModel(RestaurantDocument restaurantDocument);
}
