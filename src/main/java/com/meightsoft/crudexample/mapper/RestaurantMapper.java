package com.meightsoft.crudexample.mapper;

import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.model.RestaurantDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface RestaurantMapper {
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    RestaurantDocument toDocument(Restaurant restaurant);

    Restaurant toModel(RestaurantDocument restaurantDocument);
}
