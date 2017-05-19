package core.order.api;

import java.util.UUID;

public class OrderedItem {
    private UUID productId;
    private Integer quantity;
    private Double price;
    private Double subtotal;

    public OrderedItem(UUID productId, Integer quantity, Double price, Double subtotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public OrderedItem() { }

    public UUID getProductId() { return productId; }

    public Integer getQuantity() { return quantity; }

    public Double getPrice() { return price; }

    public Double getSubtotal() { return subtotal; }

}
