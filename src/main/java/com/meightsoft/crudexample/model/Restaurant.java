package com.meightsoft.crudexample.model;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    private Boolean isVegan = false;
}
