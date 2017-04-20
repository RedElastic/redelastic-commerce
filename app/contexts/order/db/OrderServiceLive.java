package contexts.order.db;

import contexts.order.api.Order;
import contexts.order.api.OrderService;
import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;

import java.util.List;
import java.util.UUID;

/**
 * Implementation details, such as ebean entities, should never "leak" out of the live package.
 * These are implementation details that must be kept isolated in order to move to microservices, etc.
 * Adhere to API spec and only accept/return API pojos.
 */
public class OrderServiceLive implements OrderService {

    @Override
    public UUID placeOrder(ShippingInfo shippingInfo, List<OrderedItem> items) {
        // TODO create ebeans
        // 1. order UUID
        // 2. create OrderItem ebeans & save
        // 3. create ShippingItem ebean & save
        // 4. create order ebean & save
        // 5. build Order POJO and return
        return UUID.randomUUID();
    }
}
