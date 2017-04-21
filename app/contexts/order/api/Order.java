package contexts.order.api;

import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;
    private final ShippingInfo shippingInfo;
    private final List<OrderedItem> items;

    public Order(UUID id, ShippingInfo shippingInfo, List<OrderedItem> items) {
        this.id = id;
        this.shippingInfo = shippingInfo;
        this.items = items;
    }

    public ShippingInfo getShippingInfo() { return shippingInfo; }

    public List<OrderedItem> getItems() { return items; }

    public UUID getId() { return id; }
}
