package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import contexts.order.api.Order;
import contexts.order.api.OrderService;
import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import contexts.product.api.ProductService;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import websockets.WebSocketsEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderController extends Controller {

    private final OrderService orderService;
    private final ProductService productService;

    @Inject
    HttpExecutionContext ec; // must have in scope when using CompletionStage<T> inside actions

    @Inject
    public OrderController(OrderService orderService, ProductService ps) {
        this.orderService = orderService;
        this.productService = ps;
    }

    public Result checkout() {
        JsonNode json = request().body().asJson();
        ShippingInfo shippingInfo = Json.fromJson(json.findPath("shippingInfo"), ShippingInfo.class);
        List<OrderedItem> items = new ArrayList<>();
        for (JsonNode node : json.withArray("items")) {
            OrderedItem item = new OrderedItem(
                UUID.fromString(node.get("productId").asText()),
                node.get("quantity").asInt(),
                node.get("price").asDouble(),
                node.get("subtotal").asDouble());
            items.add(item);
        }
        return ok(Json.toJson(orderService.placeOrder(shippingInfo, items)));
    }

    public Result findOrder(String orderId) {
        Order order = orderService.findOrder(UUID.fromString(orderId));

        List<DisplayOrderItem> displayItems = new ArrayList<>();

        for (OrderedItem item : order.getItems()) {
            displayItems.add(new DisplayOrderItem(
                                    item.getProductId(),
                                    productService.getProduct(item.getProductId()).getName(),
                                    item.getQuantity(),
                                    item.getPrice(),
                                    item.getSubtotal()));
        }

        JsonNode shippingInfo = Json.toJson(order.getShippingInfo());
        JsonNode totals = Json.toJson(order.getOrderTotals());
        JsonNode items = Json.toJson(displayItems);

        ObjectNode result = Json.newObject();
        result.put("shippingInfo", shippingInfo);
        result.put("items", items);
        result.put("totals", totals);
        return ok(result);
    }

    class DisplayOrderItem {
        private UUID productId;
        private String name;
        private Integer quantity;
        private Double price;
        private Double subtotal;

        public DisplayOrderItem(UUID productId, String name, Integer quantity, Double price, Double subtotal) {
            this.productId = productId;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.subtotal = subtotal;
        }

        public DisplayOrderItem() {}

        public UUID getProductId() { return productId; }

        public String getName() { return name; }

        public Integer getQuantity() { return quantity; }

        public Double getPrice() { return price; }

        public Double getSubtotal() { return subtotal; }
    }
}

