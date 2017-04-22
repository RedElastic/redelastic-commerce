package contexts.cart.api;

import java.io.Serializable;
import java.util.UUID;

public class CartItem implements Serializable {
    final private UUID id;
    final private Integer quantity;
    final private Double price;

    public CartItem(UUID id, Integer quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Probably should be Dollars and Cents as Integers (See Price in the Product Domain)
     * @return
     */
    public Double getPrice() {
        return price;
    }
}
