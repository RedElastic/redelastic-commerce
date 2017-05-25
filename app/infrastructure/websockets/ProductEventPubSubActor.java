package infrastructure.websockets;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.IntNode;
import core.product.api.ProductEvent;
import play.Logger;
import play.libs.Json;

/**
 * Example of an actor that subscribes to a topic for pub/sub from an event bus and publishes
 * any messages to an actor in the WebSocket stream.
 * TODO this should be refactored to follow onion architecture to prevent corruption that would make extracting to a microservice difficult.
 */

public class ProductEventPubSubActor extends AbstractActor {

    private final ActorRef out;
    private final WebSocketsEventBus eventBus;

    public ProductEventPubSubActor(ActorRef out, WebSocketsEventBus eventBus) {
        this.out = out;
        this.eventBus = eventBus;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ProductEvent.class, productEvent -> {
                    Logger.info("received a product update. Sending to websocket!");
                    out.tell(Json.toJson(productEvent), self());
                }).
                match(IntNode.class, topic -> {
                    Logger.info("subscribing to topic {}", topic);
                    eventBus.subscribe(self(), topic.asText());
                }).
                matchAny(o -> Logger.error("received unknown message " + o.getClass())).build();
    }
}
