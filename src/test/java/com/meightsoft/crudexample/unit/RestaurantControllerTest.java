package com.meightsoft.crudexample.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meightsoft.crudexample.controller.RestaurantController;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.facade.RestaurantOrderFacade;
import com.meightsoft.crudexample.fixtures.RestaurantFixtures;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantOrderFacade restaurantOrderFacade;

    @Test
    public void createShouldReturnCreated() throws Exception {
        // Given
        when(restaurantService.save(any(Restaurant.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));
        var objectMapper = new ObjectMapper();

        // When
        // Then
        this.mockMvc
                .perform(post("/restaurants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(RestaurantFixtures.getSingleRestaurant(false)))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(RestaurantFixtures.getSingleRestaurant(true))
                ));
    }

    @Test
    public void createShouldReturnBadRequest() throws Exception {
        // Given
        var invalidRestaurant = RestaurantFixtures.getSingleRestaurant(false);
        invalidRestaurant.setName(null);
        var objectMapper = new ObjectMapper();

        // When
        // Then
        this.mockMvc
                .perform(post("/restaurants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRestaurant))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getShouldReturnOk() throws Exception {
        // Given
        when(restaurantService.get(any(Long.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));
        var objectMapper = new ObjectMapper();

        // When
        // Then
        this.mockMvc
                .perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(RestaurantFixtures.getSingleRestaurant(true))
                ));
    }

    @Test
    public void getShouldReturnNotFound() throws Exception {
        // Given
        when(restaurantService.get(any(Long.class))).thenThrow(new EntityNotFoundException("The specified item doesn't exist"));

        // When
        // Then
        this.mockMvc
                .perform(get("/restaurants/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listShouldReturnOkWithItems() throws Exception {
        // Given
        when(restaurantService.list()).thenReturn(RestaurantFixtures.getRestaurantList());
        var objectMapper = new ObjectMapper();

        // When
        // Then
        this.mockMvc
                .perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(RestaurantFixtures.getRestaurantList())
                ));
    }

    @Test
    public void updateShouldReturnOk() throws Exception {
        // Given
        when(restaurantService.save(any(Restaurant.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));
        var objectMapper = new ObjectMapper();

        // When
        // Then
        this.mockMvc
                .perform(put("/restaurants/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(RestaurantFixtures.getSingleRestaurant(false)))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(RestaurantFixtures.getSingleRestaurant(true))
                ));
    }

    @Test
    public void deleteShouldReturnOk() throws Exception {
        // Given
        doNothing().when(restaurantService).delete(anyLong());

        // When
        // Then
        this.mockMvc
                .perform(delete("/restaurants/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldReturnNotFound() throws Exception {
        // Given
        doThrow(new EntityNotFoundException("The specified item doesn't exist")).when(restaurantOrderFacade).deleteRestaurant(anyLong());

        // When
        // Then
        this.mockMvc
                .perform(delete("/restaurants/1"))
                .andExpect(status().isNotFound());
    }
}
