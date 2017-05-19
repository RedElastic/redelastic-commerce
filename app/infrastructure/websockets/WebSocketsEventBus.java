package infrastructure.websockets;

import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;

/**
 * Pub/sub mechanism for infrastructure.websockets.
 * The websocket subscribes to a channel/topic (eg product id)
 * Then any updates to are published on that channel/topic.
 */

public class WebSocketsEventBus extends LookupEventBus<WebSocketEvent, ActorRef, String> {

    /**
     * Default map size. Should be larger than the number of products to avoid resizing ops.
     * @return
     */
    @Override
    public int mapSize() {
        return 15;
    }

    /**
     * Takes the ID of a product event to determine the topic for publishing updates.
     * @param event
     * @return
     */
    @Override
    public String classify(WebSocketEvent event) {
        return event.getChannel();
    }

    /**
     * Subscribers are actors in our use case so we send a message to the infrastructure.websockets actor that's subscribed to a topic.
     * @param event a ProductEvent
     * @param subscriber the websocket ActorRef
     */
    @Override
    public void publish(WebSocketEvent event, ActorRef subscriber) {
        subscriber.tell(event, ActorRef.noSender());
    }

    /**
     * Boilerplate - Akka needs an explicit ordering of the subscribers.
     * @param subscriberA
     * @param subscriberB
     * @return
     */
    @Override
    public int compareSubscribers(ActorRef subscriberA, ActorRef subscriberB) {
        return subscriberA.compareTo(subscriberB);
    }

}
