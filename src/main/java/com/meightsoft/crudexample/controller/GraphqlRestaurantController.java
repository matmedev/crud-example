package com.meightsoft.crudexample.controller;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.graphql.GraphqlRestaurantMapper;
import com.meightsoft.crudexample.model.graphql.Restaurant;
import com.meightsoft.crudexample.model.graphql.RestaurantInput;
import com.meightsoft.crudexample.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@AllArgsConstructor
public class GraphqlRestaurantController {

    private final RestaurantService restaurantService;
    private final GraphqlRestaurantMapper graphqlRestaurantMapper;

    @QueryMapping("restaurants")
    public Restaurant[] getAllRestaurants() {
        return restaurantService.list().stream().map(graphqlRestaurantMapper::toGraphqlModel).toArray(Restaurant[]::new);
    }

    @QueryMapping("restaurant")
    public Restaurant getRestaurant(@Argument String id) throws EntityNotFoundException {
        return graphqlRestaurantMapper.toGraphqlModel(restaurantService.get(id));
    }

    @MutationMapping("createRestaurant")
    public Restaurant createRestaurant(@Argument RestaurantInput restaurant) {
        var result = restaurantService.save(
                graphqlRestaurantMapper.toModel(restaurant)
        );

        return graphqlRestaurantMapper.toGraphqlModel(result);
    }

    @MutationMapping("updateRestaurant")
    public Restaurant updateRestaurant(@Argument String id, @Argument RestaurantInput restaurant) throws EntityNotFoundException {
        if (!restaurantService.exists(id)) {
            throw new EntityNotFoundException("Restaurant not found");
        }

        var model = graphqlRestaurantMapper.toModel(restaurant);
        model.setId(id);

        var result = restaurantService.save(
                graphqlRestaurantMapper.toModel(restaurant)
        );

        return graphqlRestaurantMapper.toGraphqlModel(result);
    }

    @MutationMapping("deleteRestaurant")
    public Restaurant deleteRestaurant(@Argument String id) throws EntityNotFoundException {
        var restaurant = restaurantService.get(id);

        restaurantService.delete(id);

        return graphqlRestaurantMapper.toGraphqlModel(restaurant);
    }
}
