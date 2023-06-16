package com.meightsoft.crudexample.constants;

public enum OrderStatus {
    PENDING,
    ACCEPTED,
    IN_DELIVERY,
    DELIVERED;

    private OrderStatus() {

    }

    public OrderStatus getNext() {
        var values = OrderStatus.values();
        return values[(this.ordinal() + 1) % values.length];
    }
}
