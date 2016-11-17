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

public class WebSocketActor extends AbstractActor {

    private final ActorRef out;
    private final WebSocketsEventBus eventBus;

    public WebSocketActor(ActorRef out, WebSocketsEventBus eventBus) {
        this.out = out;
        this.eventBus = eventBus;

        receive(ReceiveBuilder.
                match(ProductEvent.class, productEvent -> { //Subscribed event received
                    Logger.info("received a product update. Sending to websocket!");
                    out.tell(Json.toJson(productEvent), self());
                }).
                match(IntNode.class, topic -> { //Subscribe to topic. TODO could be json instead: {sub: "topic"}
                    Logger.info("subscribing to topic {}", topic);
                    eventBus.subscribe(self(), topic.asText());
                }).
                matchAny(o -> Logger.error("received unknown message " + o.getClass())).build()
        );
    }
}
