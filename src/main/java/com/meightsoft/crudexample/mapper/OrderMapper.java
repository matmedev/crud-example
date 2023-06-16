package com.meightsoft.crudexample.mapper;

import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.model.OrderDocument;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrdersMapper.class})
public interface OrderMapper {

    OrderDocument toDocument(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    OrderDocument updateDocument(@MappingTarget OrderDocument orderDocument, Order order);

    Order toModel(OrderDocument order);
}
