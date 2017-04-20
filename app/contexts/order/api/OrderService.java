package contexts.order.api;

import java.util.List;

public interface OrderService {
    void placeOrder(ShippingInfo shippingInfo, List<OrderedItem> items);
}
