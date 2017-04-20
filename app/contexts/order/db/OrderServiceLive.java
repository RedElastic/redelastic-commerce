package contexts.order.db;

import contexts.order.api.Order;
import contexts.order.api.OrderService;
import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import contexts.order.db.models.OrderBean;
import play.Logger;
import play.libs.Json;

import java.util.List;

/**
 * Implementation details, such as ebean entities, should never "leak" out of the live package.
 * These are implementation details that must be kept isolated in order to move to microservices, etc.
 * Adhere to API spec and only accept/return API pojos.
 */
public class OrderServiceLive implements OrderService {

    @Override
    public void placeOrder(ShippingInfo shippingInfo, List<OrderedItem> items) {
        // TODO
    }
}
