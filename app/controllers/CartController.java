package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import contexts.cart.api.CartItem;
import contexts.cart.api.CartService;
import javaslang.collection.HashSet;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javaslang.collection.Set;

public class CartController extends Controller {
    final private CartService service;

    @Inject
    public CartController(CartService service) {
        this.service = service;
    }

    public CompletionStage<Result> getCart(String userId) {
        System.out.println("userId: " + userId);
        return service.getCartContents(userId).thenApply(cart -> ok(Json.toJson(cart.toJavaSet())));
    }

    public Result updateCart() {
        JsonNode json = request().body().asJson();
        String userId = json.get("userId").asText();
        System.out.println("userId: " + userId);
        Set<CartItem> items = HashSet.empty();
        for (JsonNode node : json.withArray("items")) {
            CartItem item = new CartItem(
                UUID.fromString(node.get("productId").asText()),
                node.get("quantity").asInt(),
                node.get("price").asDouble());
            items.add(item);
        }
        service.updateCartItems(userId, items);
        return ok();
    }

    public Result deleteCart(String userId) {
        service.emptyCart(userId);
        return ok();
    }
}
