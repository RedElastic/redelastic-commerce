package contexts.order.api;


import websockets.WebSocketEvent;

public class OrderEvent implements WebSocketEvent {
    private final Order order;
    private EventType type;

    public OrderEvent(Order order, EventType type) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String getChannel() {
        return "websocket-order-events";
    }

    public enum EventType {
        Purchased,
        Modified,
        Processed,
        Cancelled
    }
}
