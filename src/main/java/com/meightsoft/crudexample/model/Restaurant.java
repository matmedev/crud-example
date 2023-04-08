package com.meightsoft.crudexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    // TODO validation annotations

    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private Boolean isVegan;
}
