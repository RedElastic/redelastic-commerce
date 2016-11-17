package contexts.product.api;

import contexts.pricing.api.Price;
import contexts.product.api.Product;

import java.util.Optional;

public class ProductEvent {
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
