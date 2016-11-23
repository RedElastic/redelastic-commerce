package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import contexts.order.api.Order;
import contexts.order.api.OrderEvent;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.admin.index;
import websockets.ActorBackedWebSocket;
import websockets.OrderEventWebSocketActor;
import websockets.WebSocketsEventBus;

import javax.inject.Inject;

public class OrderDashboardController extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final ActorBackedWebSocket sockets;
    private final WebSocketsEventBus eventBus;

    @Inject
    public OrderDashboardController(ActorSystem actorSystem, Materializer materializer, WebSocketsEventBus eventBus) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.eventBus = eventBus;
        this.sockets = new ActorBackedWebSocket() {
            @Override
            public ActorRef createWebsocketActor(ActorRef webSocketOut) {
                return actorSystem.actorOf(Props.create(OrderEventWebSocketActor.class, webSocketOut, eventBus));
            }
        };
    }

    public Result index() {
        return ok(index.render());
    }

    public WebSocket webSocket() {
        return sockets.webSocket(actorSystem, materializer);
    }

    public Result testUpdate() {
        eventBus.publish(new OrderEvent(new Order(
            System.currentTimeMillis(),
            "first",
            "last",
            "email@address.com",
            "(test order)",
            "street",
            "city",
            "province",
            "postal code"),
            OrderEvent.EventType.Purchased
        ));

        return ok("done!");
    }
}
