package com.meightsoft.crudexample.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private Boolean isVegan;

    private Boolean isOnWolt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
