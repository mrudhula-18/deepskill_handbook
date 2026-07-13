package com.dn5.week2.logging;

/**
 * Demo runner that exercises {@link OrderService} so that INFO, WARN and
 * ERROR log lines are all visible on the console.
 */
public class Main {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        Order order = Order.builder()
                .id(1001L)
                .customerName("Jane Doe")
                .amount(249.99)
                .status("NEW")
                .build();

        orderService.placeOrder(order);
        orderService.cancelOrder(order.getId());
        orderService.processOrderWithSimulatedFailure(order.getId());
    }
}
