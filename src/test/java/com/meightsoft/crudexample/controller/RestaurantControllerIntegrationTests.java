package com.meightsoft.crudexample.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meightsoft.crudexample.mapper.mapstruct.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-integration.properties")
public class RestaurantControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateRestaurantWithValidData() {
        // Given

        // When
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var response = restTemplate.postForEntity("/restaurants", testRestaurant, Restaurant.class);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseRestaurant = response.getBody();
        assertNotNull(responseRestaurant);

        assertEquals(testRestaurant.toBuilder().id(responseRestaurant.getId()).build(), responseRestaurant);
    }

    @Test
    public void testCreateRestaurantWithMissingName() {
        // Given

        // When
        var testRestaurant = Restaurant.builder()
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var response = restTemplate.postForEntity("/restaurants", testRestaurant, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateRestaurantWithMissingAddress() {
        // Given

        // When
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .phoneNumber("+1234567890")
                .build();
        var response = restTemplate.postForEntity("/restaurants", testRestaurant, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateRestaurantWithMissingPhoneNumber() {
        // Given

        // When
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .build();
        var response = restTemplate.postForEntity("/restaurants", testRestaurant, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetRestaurantWithValidId() {
        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurant = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurant.getId();

        testRestaurant.setId(savedRestaurant.getId());

        // When
        var response = restTemplate.getForEntity("/restaurants/" + testRestaurantId, Restaurant.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testRestaurant, response.getBody());
    }

    @Test
    public void testGetRestaurantWithInvalidId() {
        // Given

        // When
        var response = restTemplate.getForEntity("/restaurants/" + 123456, Restaurant.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testListRestaurantsWithNoItems() {
        // Given
        restaurantRepository.deleteAll();

        // When
        var response = restTemplate.getForEntity("/restaurants", List.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testListRestaurantsWithItems() throws JsonProcessingException {
        // Given
        restaurantRepository.deleteAll();
        var testRestaurant1 = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();

        var testRestaurant2 = testRestaurant1.toBuilder()
                .name("Test Restaurant 2")
                .build();

        var testRestaurant3 = testRestaurant1.toBuilder()
                .name("Test Restaurant 2")
                .build();

        var testRestaurantEntity1 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant1));
        testRestaurant1.setId(testRestaurantEntity1.getId());
        var testRestaurantEntity2 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant2));
        testRestaurant2.setId(testRestaurantEntity2.getId());
        var testRestaurantEntity3 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant3));
        testRestaurant3.setId(testRestaurantEntity3.getId());

        var expectedArray = new Restaurant[]{testRestaurant1, testRestaurant2, testRestaurant3};

        // When
        var response = restTemplate.getForEntity("/restaurants", String.class);
        var responseArray = objectMapper.readValue(response.getBody(), Restaurant[].class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertArrayEquals(expectedArray, responseArray);
    }

    @Test
    public void testUpdateRestaurantWithValidData() {
        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurantEntity.getId();

        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);

        var updatedRestaurant = savedRestaurant.toBuilder()
                .name("Updated Restaurant")
                .build();

        // When
        var requestEntity = new HttpEntity<>(updatedRestaurant);
        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRestaurant, response.getBody());
    }

    @Test
    public void testUpdateRestaurantWithMissingName() {
        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurantEntity.getId();

        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);

        var updatedRestaurant = savedRestaurant.toBuilder()
                .name(null)
                .build();

        // When
        var requestEntity = new HttpEntity<>(updatedRestaurant);
        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateRestaurantWithMissingAddress() {

        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurantEntity.getId();

        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);

        var updatedRestaurant = savedRestaurant.toBuilder()
                .address(null)
                .build();

        // When
        var requestEntity = new HttpEntity<>(updatedRestaurant);
        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateRestaurantWithMissingPhoneNumber() {
        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurantEntity.getId();

        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);

        var updatedRestaurant = savedRestaurant.toBuilder()
                .phoneNumber(null)
                .build();

        // When
        var requestEntity = new HttpEntity<>(updatedRestaurant);
        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteRestaurantWithValidId() {
        // Given
        var testRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();
        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
        var testRestaurantId = savedRestaurantEntity.getId();

        // When
        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteRestaurantWithInvalidId() {
        // Given

        // When
        var response = restTemplate.exchange("/restaurants/123456", HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
