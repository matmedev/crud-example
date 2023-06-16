package com.meightsoft.crudexample.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurants")
public class RestaurantDocument {

    @Id
    private String id;

    private String name;

    private String address;

    private String phoneNumber;

    private Boolean isVegan;

    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{'orders': ?#{#self.id} }")
    private List<OrderDocument> orders;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
