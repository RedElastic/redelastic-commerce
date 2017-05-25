package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import core.cart.api.CartItem;
import core.cart.api.CartService;
import core.product.api.Product;
import core.product.api.ProductService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javaslang.collection.List;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class CartController extends Controller {
    final private CartService service;
    final private ProductService productService;

    @Inject
    public CartController(CartService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    public CompletionStage<Result> getCart(String userId) {
        return service.getCartContents(userId).thenApply(cart -> {
            List<DisplayCartItem> items = List.empty();

            for (CartItem item : cart) {
                Product product = productService.getProduct(item.getProductId());
                items = items.append(new DisplayCartItem(
                    item.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    item.getQuantity(),
                    item.getPrice()));
            }

            return ok(Json.toJson(items));
        });
    }

    public Result updateCart() {
        JsonNode json = request().body().asJson();
        String userId = json.get("userId").asText();
        List<CartItem> cartItems = List.empty();
        JsonNode nodes = json.get("items");

        for (JsonNode node : nodes) {
            CartItem item = new CartItem(UUID.fromString(node.get("productId").asText()), node.get("quantity").asInt(), node.get("price").asDouble());
            cartItems = cartItems.append(item);
        }
        service.updateCartItems(userId, cartItems);
        return ok();
    }

    public Result deleteCart(String userId) {
        service.emptyCart(userId);
        return ok();
    }

    class DisplayCartItem {
        private UUID productId;
        private String name;
        private String description;
        private Integer quantity;
        private Double price;

        public DisplayCartItem(UUID productId, String name, String description, Integer quantity, Double price) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
        }

        public DisplayCartItem() {}

        public UUID getProductId() { return productId; }

        public String getName() { return name; }

        public Integer getQuantity() { return quantity; }

        public Double getPrice() { return price; }

        public String getDescription() { return description; }

    }
}
