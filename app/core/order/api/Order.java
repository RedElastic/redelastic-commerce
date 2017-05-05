package core.order.api;

import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;
    private final ShippingInfo shippingInfo;
    private final List<OrderedItem> items;
    private final OrderTotals orderTotals;

    public Order(UUID id, ShippingInfo shippingInfo, List<OrderedItem> items, OrderTotals orderTotals) {
        this.id = id;
        this.shippingInfo = shippingInfo;
        this.items = items;
        this.orderTotals = orderTotals;
    }

    public ShippingInfo getShippingInfo() { return shippingInfo; }

    public List<OrderedItem> getItems() { return items; }

    public UUID getId() { return id; }

    public OrderTotals getOrderTotals() { return orderTotals; }
}
