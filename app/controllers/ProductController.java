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
import websockets.ProductEventWebSocketActor;
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
                return actorSystem.actorOf(Props.create(ProductEventWebSocketActor.class, webSocketOut, eventBus));
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

    /**
     * Test endpoint for demo
     * @return
     */
    public Result testUpdate() {
        eventBus.publish(new ProductEvent(new Product(1, "sku", "http://google.com/img.jpg", "name", "description"), Optional.empty()));
        return ok("done!");
    }
}


