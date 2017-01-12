package websockets;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.node.IntNode;
import contexts.order.api.OrderEvent;
import contexts.product.api.ProductEvent;
import play.Logger;
import play.libs.Json;

/**
 * Whenever an order is received, send the update to anyone watching the dashboard.
 */

public class OrderEventPubSubActor extends AbstractActor {
    private final ActorRef out;
    private final WebSocketsEventBus eventBus;

    public OrderEventPubSubActor(ActorRef out, WebSocketsEventBus eventBus) {
        this.out = out;
        this.eventBus = eventBus;

        eventBus.subscribe(self(), "websocket-order-events");

        receive(ReceiveBuilder.
            match(OrderEvent.class, orderEvent -> {
                Logger.info("received an order event. Sending to websocket!");
                out.tell(Json.toJson(orderEvent), self());
            }).
            matchAny(o -> Logger.error("received unknown message " + o.getClass())).build()
        );
    }
}

