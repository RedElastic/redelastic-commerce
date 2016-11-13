/**
 Copyright 2016 RedElastic Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package websockets;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.node.IntNode;
import contexts.product.live.ProductEvent;
import play.Logger;
import play.libs.Json;


public class WebSocketActor extends AbstractActor {

    private final ActorRef out;
    private final WebSocketsEventBus eventBus;

    public WebSocketActor(ActorRef out, WebSocketsEventBus eventBus) {
        this.out = out;
        this.eventBus = eventBus;

        receive(ReceiveBuilder.
                        match(ProductEvent.class, productEvent -> {
                            Logger.info("received a product update. Sending to websocket!");
                            out.tell(Json.toJson(productEvent), self());
                        }).
                        match(IntNode.class, topic -> { //TODO should be json request - looks for IntNode only
                            eventBus.subscribe(self(), topic.asText());
                            Logger.info("subscribed to topic {}", topic);
                        }).
                        matchAny(o -> Logger.info("received unknown message " + o.getClass())).build()
        );
    }
}
