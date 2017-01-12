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
import contexts.order.kafka.OrderEventPubSubActor;
import websockets.WebSocketsEventBus;
import com.google.inject.Inject;

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
            return actorSystem.actorOf(Props.create(OrderEventPubSubActor.class, webSocketOut, eventBus));
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
            "first",
            "last",
            "email@address.com",
            "Shipping option",
            "street",
            "city",
            "province",
            "postal code"),
            OrderEvent.EventType.Purchased
        ));

        return ok("done!");
    }
}
