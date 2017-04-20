package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import contexts.order.api.OrderService;
import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import websockets.WebSocketsEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        ShippingInfo shippingInfo = Json.fromJson(json.findPath("shippingInfo"), ShippingInfo.class);
        List<OrderedItem> items = new ArrayList<>();
        for (JsonNode node : json.withArray("items")) {
            OrderedItem item = new OrderedItem(
                UUID.fromString(node.get("productId").asText()),
                node.get("quantity").asInt(),
                new Double(42.99),
                new Double(42.99),
                new Double(42.99));
            items.add(item);
        }
        return ok(Json.toJson(orderService.placeOrder(shippingInfo, items)));
    }
}