package contexts.cart.api;

import java.io.Serializable;
import java.util.UUID;

public class CartItem implements Serializable {
    final private UUID id;
    final private Integer qty;
    final private Double price;

    public CartItem(UUID id, Integer qty, Double price) {
        this.id = id;
        this.qty = qty;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Integer getQty() {
        return qty;
    }

    /**
     * Probably should be Dollars and Cents as Integers (See Price in the Product Domain)
     * @return
     */
    public Double getPrice() {
        return price;
    }
}
