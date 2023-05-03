package com.meightsoft.crudexample.unit;

import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.fixtures.RestaurantFixtures;
import com.meightsoft.crudexample.mapper.RestaurantMapper;
import com.meightsoft.crudexample.model.Restaurant;
import com.meightsoft.crudexample.persistence.model.RestaurantEntity;
import com.meightsoft.crudexample.persistence.repository.RestaurantRepository;
import com.meightsoft.crudexample.service.RestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    RestaurantMapper restaurantMapper;

    @Test
    void saveShouldSaveOneRestaurant() {
        // Given
        var restaurantSaved = RestaurantFixtures.getSingleRestaurantEntity(true);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantSaved);

        when(restaurantMapper.toEntity(any(Restaurant.class))).thenReturn(restaurantSaved);
        when(restaurantMapper.toModel(any(RestaurantEntity.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));

        // When
        var restaurantToSave = RestaurantFixtures.getSingleRestaurant(false);
        var result = restaurantService.save(restaurantToSave);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(RestaurantFixtures.getSingleRestaurant(true));
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getShouldReturnRestaurant() throws EntityNotFoundException {
        // Given
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(RestaurantFixtures.getSingleRestaurantEntity(true)));
        when(restaurantMapper.toModel(any(RestaurantEntity.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));

        // When
        var result = restaurantService.get(getRandomLong());

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(RestaurantFixtures.getSingleRestaurant(true));
        verify(restaurantRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getShouldNotReturnRestaurantIfItDoesNotExist() {
        // Given
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        // Then
        Assertions.assertThrows(EntityNotFoundException.class, () -> restaurantService.get(getRandomLong()));
        verify(restaurantRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void listShouldReturnListOfRestaurants() {
        // Given
        when(restaurantRepository.findAll()).thenReturn(RestaurantFixtures.getRestaurantEntityList());
        when(restaurantMapper.toModel(any(RestaurantEntity.class))).thenReturn(RestaurantFixtures.getSingleRestaurant(true));

        // When
        var result = restaurantService.list();

        // Then
        var expected = RestaurantFixtures.getRestaurantList();
        assertThat(result).hasSize(expected.size());
        assertThat(result).hasSameElementsAs(expected);
        verify(restaurantRepository, times(1)).findAll();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void listShouldReturnEmptyListOfRestaurants() {
        // Given
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        var result = restaurantService.list();

        // Then
        assertThat(result).hasSize(0);
        verify(restaurantRepository, times(1)).findAll();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void deleteShouldDeleteRestaurant() throws EntityNotFoundException {
        // Given
        when(restaurantRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(restaurantRepository).deleteById(anyLong());

        // When
        restaurantService.delete(getRandomLong());

        // Then
        verify(restaurantRepository, times(1)).existsById(anyLong());
        verify(restaurantRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void deleteShouldNotDeleteRestaurantIfItDoesNotExist() {
        // Given
        when(restaurantRepository.existsById(anyLong())).thenReturn(false);

        // When
        // Then
        Assertions.assertThrows(EntityNotFoundException.class, () -> restaurantService.delete(getRandomLong()));
        verify(restaurantRepository, times(1)).existsById(anyLong());
        verifyNoMoreInteractions(restaurantRepository);
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().orElseThrow();
    }
}
