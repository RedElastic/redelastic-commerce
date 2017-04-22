package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import contexts.cart.api.CartItem;
import contexts.cart.api.CartService;
import contexts.cart.cluster.CartClusterService;
import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartController extends Controller {
    final private CartService service;

    @Inject
    public CartController(CartService service) {
        this.service = service;
    }

    public Result getCart(String userId) {
        return ok(Json.toJson(service.getCartContents(userId)));
    }

    public Result updateCart() {
        JsonNode json = request().body().asJson();
        String userId = json.get("userId").asText();
        List<CartItem> items = new ArrayList<>();
        for (JsonNode node : json.withArray("items")) {
            CartItem item = new CartItem(
                UUID.fromString(node.get("productId").asText()),
                node.get("quantity").asInt(),
                node.get("price").asDouble());
            items.add(item);
        }
        return ok(Json.toJson(service.getCartContents(userId)));
    }

    public Result deleteCart(String userId) {
        service.emptyCart(userId);
        return ok();
    }
}
