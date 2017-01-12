package websockets;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.node.IntNode;
import contexts.product.api.ProductEvent;
import play.Logger;
import play.libs.Json;

/**
 * Example of an actor that subscribes to a topic for pub/sub from an event bus and publishes
 * any messages to an actor in the WebSocket stream.
 */

public class ProductEventWebSocketActor extends AbstractActor {

    private final ActorRef out;
    private final WebSocketsEventBus eventBus;

    public ProductEventWebSocketActor(ActorRef out, WebSocketsEventBus eventBus) {
        this.out = out;
        this.eventBus = eventBus;

        receive(ReceiveBuilder.
            match(ProductEvent.class, productEvent -> {
                Logger.info("received a product update. Sending to websocket!");
                out.tell(Json.toJson(productEvent), self());
            }).
            match(IntNode.class, topic -> {
                Logger.info("subscribing to topic {}", topic);
                eventBus.subscribe(self(), topic.asText());
            }).
            matchAny(o -> Logger.error("received unknown message " + o.getClass())).build()
        );
    }
}
