package core.order.stub;

import core.order.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation details, such as ebean entities, should never "leak" out of the live package.
 * These are implementation details that must be kept isolated in order to move to microservices, etc.
 * Adhere to API spec and only accept/return API pojos.
 */
public class OrderServiceLive implements OrderService {

    Map<UUID, Order> orders = new HashMap<>();

    @Override
    public UUID placeOrder(ShippingInfo shippingInfo, List<OrderedItem> items) {
        UUID orderId = UUID.randomUUID();

        Double subtotal = 0.0;
        for (OrderedItem item: items) {
            subtotal = subtotal + item.getSubtotal();
        }

        Double taxes = subtotal * 0.13;
        Double total = subtotal + taxes;

        OrderTotals orderTotals = new OrderTotals(subtotal, taxes, total);

        orders.put(orderId, new Order(orderId, shippingInfo, items, orderTotals));
        return orderId;
    }

    @Override
    public Order findOrder(UUID orderId) {
        return orders.get(orderId);
    }

}
