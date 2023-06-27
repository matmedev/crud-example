package com.meightsoft.crudexample.mapper.graphql;

import com.meightsoft.crudexample.mapper.OrderMapper;
import com.meightsoft.crudexample.model.graphql.Restaurant;
import com.meightsoft.crudexample.model.graphql.RestaurantInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface GraphqlRestaurantMapper {

    Restaurant toGraphqlModel(com.meightsoft.crudexample.model.Restaurant restaurant);

    com.meightsoft.crudexample.model.Restaurant toModel(Restaurant restaurant);

    com.meightsoft.crudexample.model.Restaurant toModel(RestaurantInput restaurant);
}
