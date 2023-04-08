package com.meightsoft.crudexample.service;


import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.mapstruct.OrderMapper;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.repository.OrderRepository;
import com.meightsoft.crudexample.validator.OrderValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final RestaurantService restaurantService;

    // TODO CreateOrderDto (with restaurantId) + findById
    public Order create(Order order) throws EntityNotFoundException {
        log.debug("OrderService::create [order={}]", order);

        var orderEntity = orderMapper.toEntity(order);
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    // TODO add
    //    @Transactional

    public Order get(Long orderId) throws EntityNotFoundException {
        log.debug("OrderService::get [orderId={}]", orderId);

        orderValidator.validateOnGet(orderId);

        var optionalOrder = orderRepository.findById(orderId);

        return orderMapper.toModel(optionalOrder.get());
    }

    public List<Order> listByRestaurantId(Long restaurantId) throws EntityNotFoundException {
        log.debug("OrderService::listByRestaurantId [restaurantId={}]", restaurantId);

        orderValidator.validateOnListByRestaurantId(restaurantId);

        var orders = orderRepository.findAllByRestaurantId(restaurantId);

        return StreamSupport.stream(orders.spliterator(), false)
                .map(orderMapper::toModel)
                .toList();
    }

    public Order update(Order order) throws EntityNotFoundException {
        log.debug("OrderService::update [order={}]", order);

        orderValidator.validateOnUpdate(order);

        var optionalOrder = orderRepository.findById(order.getId());
        var orderEntity = orderMapper.updateEntity(optionalOrder.get(), order);
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    public Order updateStatus(Long orderId, OrderStatus status) throws EntityNotFoundException {
        log.debug("OrderService::updateStatus [orderId={}, status={}]", orderId, status);

        orderValidator.validateOnUpdateStatus(orderId, status);

        var optionalOrder = orderRepository.findById(orderId);
        var orderEntity = orderMapper.updateEntity(optionalOrder.get(), Order.builder().status(status).build());
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    public void delete(Long orderId) throws EntityNotFoundException {
        log.debug("OrderService::delete [orderId={}]", orderId);

        orderValidator.validateOnDelete(orderId);

        orderRepository.deleteById(orderId);
    }

    public void deleteAllByRestaurantId(Long restaurantId) {
        log.debug("OrderService::deleteAllByRestaurantId [restaurantId={}]", restaurantId);

        orderRepository.deleteAllByRestaurantId(restaurantId);
    }
}
