package com.meightsoft.crudexample.service;


import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.OrderMapper;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.repository.OrderRepository;
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

    public Order create(Order order) {
        log.debug("OrderService::create [order={}]", order);

        order.setStatus(OrderStatus.PENDING);

        var orderEntity = orderMapper.toEntity(order);
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    public Order get(Long orderId) throws EntityNotFoundException {
        log.debug("OrderService::get [orderId={}]", orderId);

        var optionalOrderEntity = orderRepository.findById(orderId);
        var orderEntity = optionalOrderEntity.orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return orderMapper.toModel(orderEntity);
    }

    public List<Order> listByRestaurantId(Long restaurantId) throws EntityNotFoundException {
        log.debug("OrderService::listByRestaurantId [restaurantId={}]", restaurantId);

        var orders = orderRepository.findAllByRestaurantId(restaurantId);

        return StreamSupport.stream(orders.spliterator(), false)
                .map(orderMapper::toModel)
                .toList();
    }

    public Order update(Order order) throws EntityNotFoundException {
        log.debug("OrderService::update [order={}]", order);

        var optionalOrder = orderRepository.findById(order.getId());
        var orderEntity = orderMapper.updateEntity(optionalOrder.get(), order);
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    public Order updateStatus(Long orderId, OrderStatus status) throws EntityNotFoundException {
        log.debug("OrderService::updateStatus [orderId={}, status={}]", orderId, status);

        // TODO validation

        var optionalOrder = orderRepository.findById(orderId);
        var orderEntity = orderMapper.updateEntity(optionalOrder.get(), Order.builder().status(status).build());
        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toModel(orderEntity);
    }

    public void delete(Long orderId) throws EntityNotFoundException {
        log.debug("OrderService::delete [orderId={}]", orderId);

        orderRepository.deleteById(orderId);
    }

    public void deleteAllByRestaurantId(Long restaurantId) {
        log.debug("OrderService::deleteAllByRestaurantId [restaurantId={}]", restaurantId);

        orderRepository.deleteAllByRestaurantId(restaurantId);
    }
}
