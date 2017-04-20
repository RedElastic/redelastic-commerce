package contexts.order.api;

import java.util.UUID;

public class OrderedItem {
    private UUID productId;
    private Integer quantity;
    private Double subtotal;
    private Double tax;
    private Double total;

    public OrderedItem(UUID productId, Integer quantity, Double subtotal, Double tax, Double total) {
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public OrderedItem() { }

    public UUID getProductId() { return productId; }

    public Integer getQuantity() { return quantity; }

    public Double getSubtotal() { return subtotal; }

    public Double getTax() { return tax; }

    public Double getTotal() { return total; }

}
