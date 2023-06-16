package com.meightsoft.crudexample.persistence.model;

import com.meightsoft.crudexample.constants.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;

    private String address;

    private Integer price;

    private OrderStatus status;

    @DocumentReference
    private RestaurantDocument restaurant;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
