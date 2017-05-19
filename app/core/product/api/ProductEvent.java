package core.product.api;

import infrastructure.websockets.WebSocketEvent;

import java.util.Optional;

public class ProductEvent implements WebSocketEvent {
    private final Product product;
    private final Optional<Price> price;

    public ProductEvent(Product product, Optional<Price> price) {
        this.product = product;
        this.price = price;
    }

    public String getChannel() {
        return product.getId().toString();
    }

    public Product getProduct() {
        return product;
    }

    public Optional<Price> getPrice() {
        return price;
    }
}
