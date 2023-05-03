package com.meightsoft.crudexample.mapper;

import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.model.OrderEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface OrderMapper {

    OrderEntity toEntity(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    OrderEntity updateEntity(@MappingTarget OrderEntity orderEntity, Order order);

    Order toModel(OrderEntity order);
}
