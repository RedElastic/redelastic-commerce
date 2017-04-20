package contexts.order.api;

import java.util.List;

public class Order {

    private final ShippingInfo shippingInfo;
    private final List<OrderedItem> items;

    public Order(ShippingInfo shippingInfo, List<OrderedItem> items) {
        this.shippingInfo = shippingInfo;
        this.items = items;
    }

}
