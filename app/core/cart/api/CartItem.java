package core.cart.api;

import java.io.Serializable;
import java.util.UUID;

public class CartItem implements Serializable {
    final private UUID productId;
    final private Integer quantity;
    final private Double price;

    public CartItem(UUID productId, Integer quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() { return quantity; }

    public Double getPrice() {
        return price;
    }

    public boolean equals(Object o) {
        return (o instanceof CartItem) && ((CartItem) o).getProductId().equals(this.getProductId());
    }

    public int hashCode() {
        return productId.hashCode();
    }
}
