package com.meightsoft.crudexample.validator;

import com.meightsoft.crudexample.constants.OrderStatus;
import com.meightsoft.crudexample.exceptions.EntityNotFoundException;
import com.meightsoft.crudexample.model.Order;
import com.meightsoft.crudexample.persistence.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderValidator {

    private final OrderRepository orderRepository;

    private final RestaurantValidator restaurantValidator;

    public void validateOnCreate(Order order, Long restaurantId) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnCreate [order={}, restaurantId={}]", order, restaurantId);

        restaurantValidator.validateOnGet(restaurantId);
    }

    public void validateOnGet(Long orderId) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnGet [orderId={}]", orderId);

        validateIfExists(orderId);
    }

    public void validateOnListByRestaurantId(Long restaurantId) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnListByRestaurantId [restaurantId={}]", restaurantId);

        restaurantValidator.validateOnGet(restaurantId);
    }

    public void validateOnUpdate(Order order) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnUpdate [order={}]", order);

        validateIfExists(order.getId());
    }

    public void validateOnUpdateStatus(Long orderId, OrderStatus status) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnStatusUpdate [orderId={}]", orderId);

        validateIfExists(orderId);

        // TODO check if state transition is valid
    }

    public void validateOnDelete(Long order) throws EntityNotFoundException {
        log.debug("OrderValidator::validateOnDelete [orderId={}]", order);

        validateIfExists(order);
    }

    private void validateIfExists(Long orderId) throws EntityNotFoundException {
        log.trace("OrderValidator::validateIfExist [orderId={}]", orderId);

        if (orderId == null || !orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found");
        }
    }
}
