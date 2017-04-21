package contexts.order.api;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID placeOrder(ShippingInfo shippingInfo, List<OrderedItem> items);
    Order findOrder(UUID orderId);
}
