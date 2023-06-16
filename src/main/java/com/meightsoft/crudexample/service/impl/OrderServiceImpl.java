package com.meightsoft.crudexample.service.impl;

import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.mapper.OrderMapper;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.repository.OrderRepository;
import com.meightsoft.crudexample.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        log.debug("OrderService::create [order={}]", order);

        order.setStatus(OrderStatus.PENDING);

        var orderDocument = orderMapper.toDocument(order);
        orderDocument = orderRepository.save(orderDocument);

        return orderMapper.toModel(orderDocument);
    }

    @Override
    public Order get(String orderId) throws EntityNotFoundException {
        log.debug("OrderService::get [orderId={}]", orderId);

        var optionalOrderDocument = orderRepository.findById(orderId);
        var orderDocument = optionalOrderDocument.orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return orderMapper.toModel(orderDocument);
    }

    @Override
    public List<Order> list() {
        log.debug("OrderService::list");

        var orders = orderRepository.findAll();

        return orders.stream().map(orderMapper::toModel).toList();
    }

    @Override
    public List<Order> listByRestaurantId(String restaurantId) {
        log.debug("OrderService::listByRestaurantId [restaurantId={}]", restaurantId);

        var orders = orderRepository.findAllByRestaurantId(restaurantId);

        return orders.stream().map(orderMapper::toModel).toList();
    }

    @Override
    public Order update(Order order) throws EntityNotFoundException {
        log.debug("OrderService::update [order={}]", order);

        var optionalOrderDocument = orderRepository.findById(order.getId());
        var orderDocument = optionalOrderDocument.orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderDocument = orderMapper.updateDocument(orderDocument, order);
        orderDocument = orderRepository.save(orderDocument);

        return orderMapper.toModel(orderDocument);

    }

    @Override
    public Order updateStatus(String orderId) throws EntityNotFoundException {
        log.debug("OrderService::updateStatus [orderId={}]", orderId);

        var optionalOrderDocument = orderRepository.findById(orderId);
        var orderDocument = optionalOrderDocument.orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderDocument = orderMapper.updateDocument(orderDocument, Order.builder().status(orderDocument.getStatus().getNext()).build());
        orderDocument = orderRepository.save(orderDocument);

        return orderMapper.toModel(orderDocument);
    }

    @Override
    public void delete(String orderId) throws EntityNotFoundException {
        log.debug("OrderService::delete [orderId={}]", orderId);

        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);

        } else {
            throw new EntityNotFoundException("Order not found");
        }
    }

    @Override
    public void deleteAllByRestaurantId(String restaurantId) {
        log.debug("OrderService::deleteAllByRestaurantId [restaurantId={}]", restaurantId);

        orderRepository.deleteAllByRestaurantId(restaurantId);
    }
}
