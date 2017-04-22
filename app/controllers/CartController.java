package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import contexts.cart.api.CartItem;
import contexts.cart.api.CartService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class CartController extends Controller {
    final private CartService service;

    @Inject
    public CartController(CartService service) {
        this.service = service;
    }

    public CompletionStage<Result> getCart(String userId) {
        return service.getCartContents(userId).thenApply(cart -> ok(Json.toJson(cart)));
    }

    public Result updateCart() {
        JsonNode json = request().body().asJson();
        String userId = json.get("userId").asText();
        List<CartItem> cartItems = new ArrayList<>();
        JsonNode nodes = json.get("items");

        for (JsonNode node : nodes) {
            CartItem item = new CartItem(UUID.fromString(node.get("productId").asText()), node.get("quantity").asInt(), node.get("price").asDouble());
            cartItems.add(item);
        }
        service.updateCartItems(userId, cartItems);
        return ok();
    }

    public Result deleteCart(String userId) {
        service.emptyCart(userId);
        return ok();
    }
}
