package com.meightsoft.crudexample.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meightsoft.crudexample.fixtures.RestaurantFixtures;
import com.meightsoft.crudexample.mapper.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration.properties")
public class RestaurantControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // TODO ignore ID, reflection based comparison

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createRestaurantShould() {
        var response =
                given().body(RestaurantFixtures.getSingleRestaurant(false))
                        .contentType(ContentType.JSON)
                        .when().post("/restaurants")
                        .then().statusCode(201)
                        .body("id", notNullValue())
                        .extract()
                        .as(Restaurant.class);
        assertThat(response).isEqualTo(RestaurantFixtures.getSingleRestaurant(false).toBuilder().id(response.getId()).build());
    }

    @Test
    public void testCreateRestaurantWithMissingName() {
        var invalidRestaurant = Restaurant.builder()
                .address("Test Address")
                .phoneNumber("+1234567890")
                .build();

        given().body(invalidRestaurant)
                .contentType(ContentType.JSON)
                .when().post("/restaurants")
                .then().statusCode(400);
    }

    @Test
    public void testCreateRestaurantWithMissingAddress() {
        var invalidRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .phoneNumber("+1234567890")
                .build();
        given().body(invalidRestaurant)
                .contentType(ContentType.JSON)
                .when().post("/restaurants")
                .then().statusCode(400);
    }

    @Test
    public void testCreateRestaurantWithMissingPhoneNumber() {
        var invalidRestaurant = Restaurant.builder()
                .name("Test Restaurant")
                .address("Test Address")
                .build();
        given().body(invalidRestaurant)
                .contentType(ContentType.JSON)
                .when().post("/restaurants")
                .then().statusCode(400);
    }

//    @Test
//    public void testGetRestaurantWithValidId() throws JsonProcessingException {
//        // Given
//        var testRestaurant = RestaurantFixtures.getSingleRestaurant(false);
//        var savedRestaurant = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurant.getId();
//
//        testRestaurant.setId(savedRestaurant.getId());
//
//        var objectMapper = new ObjectMapper();
//
//        get(String.format("/restaurants/%s", testRestaurantId))
//                .then().statusCode(200)
//                .body(equalTo(objectMapper.writeValueAsString(testRestaurant)));
//    }

//    @Test
//    public void testGetRestaurantWithInvalidId() {
//        get("/restaurants/1").then().statusCode(404);
//    }

//    @Test
//    public void testListRestaurantsWithNoItems() {
//        // Given
//        restaurantRepository.deleteAll();
//
//        // When
//        var response = restTemplate.getForEntity("/restaurants", List.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(0, response.getBody().size());
//    }
//
//    @Test
//    public void testListRestaurantsWithItems() throws JsonProcessingException {
//        // Given
//        restaurantRepository.deleteAll();
//        var testRestaurant1 = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//
//        var testRestaurant2 = testRestaurant1.toBuilder()
//                .name("Test Restaurant 2")
//                .build();
//
//        var testRestaurant3 = testRestaurant1.toBuilder()
//                .name("Test Restaurant 2")
//                .build();
//
//        var testRestaurantEntity1 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant1));
//        testRestaurant1.setId(testRestaurantEntity1.getId());
//        var testRestaurantEntity2 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant2));
//        testRestaurant2.setId(testRestaurantEntity2.getId());
//        var testRestaurantEntity3 = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant3));
//        testRestaurant3.setId(testRestaurantEntity3.getId());
//
//        var expectedArray = new Restaurant[]{testRestaurant1, testRestaurant2, testRestaurant3};
//
//        // When
//        var response = restTemplate.getForEntity("/restaurants", String.class);
//        var responseArray = objectMapper.readValue(response.getBody(), Restaurant[].class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertArrayEquals(expectedArray, responseArray);
//    }
//
//    @Test
//    public void testUpdateRestaurantWithValidData() {
//        // Given
//        var testRestaurant = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurantEntity.getId();
//
//        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);
//
//        var updatedRestaurant = savedRestaurant.toBuilder()
//                .name("Updated Restaurant")
//                .build();
//
//        // When
//        var requestEntity = new HttpEntity<>(updatedRestaurant);
//        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(updatedRestaurant, response.getBody());
//    }
//
//    @Test
//    public void testUpdateRestaurantWithMissingName() {
//        // Given
//        var testRestaurant = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurantEntity.getId();
//
//        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);
//
//        var updatedRestaurant = savedRestaurant.toBuilder()
//                .name(null)
//                .build();
//
//        // When
//        var requestEntity = new HttpEntity<>(updatedRestaurant);
//        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    public void testUpdateRestaurantWithMissingAddress() {
//
//        // Given
//        var testRestaurant = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurantEntity.getId();
//
//        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);
//
//        var updatedRestaurant = savedRestaurant.toBuilder()
//                .address(null)
//                .build();
//
//        // When
//        var requestEntity = new HttpEntity<>(updatedRestaurant);
//        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    public void testUpdateRestaurantWithMissingPhoneNumber() {
//        // Given
//        var testRestaurant = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurantEntity.getId();
//
//        var savedRestaurant = restaurantMapper.toModel(savedRestaurantEntity);
//
//        var updatedRestaurant = savedRestaurant.toBuilder()
//                .phoneNumber(null)
//                .build();
//
//        // When
//        var requestEntity = new HttpEntity<>(updatedRestaurant);
//        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.PUT, requestEntity, Restaurant.class);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteRestaurantWithValidId() {
//        // Given
//        var testRestaurant = Restaurant.builder()
//                .name("Test Restaurant")
//                .address("Test Address")
//                .phoneNumber("+1234567890")
//                .build();
//        var savedRestaurantEntity = restaurantRepository.save(restaurantMapper.toEntity(testRestaurant));
//        var testRestaurantId = savedRestaurantEntity.getId();
//
//        // When
//        var response = restTemplate.exchange("/restaurants/" + testRestaurantId, HttpMethod.DELETE, null, Void.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteRestaurantWithInvalidId() {
//        // Given
//
//        // When
//        var response = restTemplate.exchange("/restaurants/123456", HttpMethod.DELETE, null, Void.class);
//
//        // Then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}
