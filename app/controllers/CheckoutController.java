package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import contexts.order.api.OrderService;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import websockets.WebSocketsEventBus;

public class CheckoutController extends Controller {

    private final OrderService orderService;
    private final WebSocketsEventBus eventBus;

    @Inject
    HttpExecutionContext ec; // must have in scope when using CompletionStage<T> inside actions

    @Inject
    public CheckoutController(OrderService orderService, WebSocketsEventBus eventBus) {
        this.orderService = orderService;
        this.eventBus = eventBus;
    }

    public Result checkout() {
        JsonNode json = request().body().asJson();
        System.out.println(json);
        return ok(Json.toJson("ok"));
    }
}