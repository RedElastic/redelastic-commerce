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

package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import contexts.product.api.Product;
import contexts.product.api.ProductService;
import contexts.product.api.ProductEvent;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.ExecutionContextExecutor;
import views.html.index;
import websockets.ActorBackedWebSocket;
import websockets.WebSocketActor;
import websockets.WebSocketsEventBus;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ProductController extends Controller {

    private final ActorSystem actorSystem;
    private final ExecutionContextExecutor ec;
    private final ProductService ps;
    private final Materializer materializer;
    private final ActorBackedWebSocket sockets;
    private final WebSocketsEventBus eventBus;

    @Inject
    public ProductController(ActorSystem actorSystem, Materializer materializer, ExecutionContextExecutor ec, ProductService ps, WebSocketsEventBus eventBus) {
        this.actorSystem = actorSystem;
        this.ec = ec;
        this.ps = ps;
        this.materializer = materializer;
        this.eventBus = eventBus;
        this.sockets = new ActorBackedWebSocket() {
            @Override
            public ActorRef createWebsocketActor(ActorRef webSocketOut) {
                return actorSystem.actorOf(Props.create(WebSocketActor.class, webSocketOut, eventBus));
            }
        };
    }

    public Result index() {
        List<Product> products = ps.lookupProducts();
        return ok(index.render("Coffee Makers", products));
    }

    public WebSocket webSocket() {
        return sockets.webSocket(actorSystem, materializer);
    }

    public Result update() {
        eventBus.publish(new ProductEvent(new Product(1, "sku", "http://google.com/img.jpg", "name", "description"), Optional.empty()));
        return ok("done!");
    }
}


