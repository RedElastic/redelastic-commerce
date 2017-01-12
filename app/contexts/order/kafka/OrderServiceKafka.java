package contexts.order.kafka;

import contexts.order.api.Order;
import contexts.order.api.OrderEvent;
import contexts.order.api.OrderService;
import websockets.WebSocketsEventBus;

public class OrderServiceKafka implements OrderService {

    private WebSocketsEventBus eventBus;

    public OrderServiceKafka() {
        this.eventBus = new OrderEventPubSubActor();
    }

    @Override
    public void saveOrder(Order order) {
        eventBus.publish(new OrderEvent(new Order(
                "first",
                "last",
                "email@address.com",
                "Shipping option",
                "street",
                "city",
                "province",
                "postal code"),
                OrderEvent.EventType.Purchased
        ));
    }

}
