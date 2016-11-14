/**
 * Copyright 2016 RedElastic Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package websockets;

import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;
import contexts.product.live.ProductEvent;

/**
 * Pub/sub mechanism for websockets.
 * The websocket subscribes to a channel/topic (eg product id)
 * Then any updates to are published on that channel/topic.
 */

public class WebSocketsEventBus extends LookupEventBus<ProductEvent, ActorRef, String> {

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
    public String classify(ProductEvent event) {
        return event.getChannel();
    }

    /**
     * Subscribers are actors in our use case so we send a message to the websockets actor that's subscribed to a topic.
     * @param event a ProductEvent
     * @param subscriber the websocket ActorRef
     */
    @Override
    public void publish(ProductEvent event, ActorRef subscriber) {
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
