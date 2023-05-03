package com.meightsoft.crudexample.fixtures;

import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.model.RestaurantEntity;

import java.util.List;

public class RestaurantFixtures {

    private static Long ID = 1L;
    private static String NAME = "Restaurant Name";
    private static String ADDRESS = "Restaurant Address";
    private static String PHONE_NUMBER = "Restaurant Phone Number";
    private static Boolean IS_VEGAN = false;

    public static Restaurant getSingleRestaurant(boolean withId) {
        var builder = Restaurant.builder()
                .id(ID)
                .name(NAME)
                .address(ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .isVegan(IS_VEGAN);

        if (withId) {
            builder.id(ID);
        }


        return builder.build();
    }

    public static RestaurantEntity getSingleRestaurantEntity(boolean withId) {
        var builder = RestaurantEntity.builder()
                .id(ID)
                .name(NAME)
                .address(ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .isVegan(IS_VEGAN);

        if (withId) {
            builder.id(ID);
        }

        return builder.build();
    }

    public static List<Restaurant> getRestaurantList() {
        return List.of(
                getSingleRestaurant(true),
                getSingleRestaurant(true),
                getSingleRestaurant(true)
        );
    }

    public static List<RestaurantEntity> getRestaurantEntityList() {
        return List.of(
                getSingleRestaurantEntity(true),
                getSingleRestaurantEntity(true),
                getSingleRestaurantEntity(true)
        );
    }
}
