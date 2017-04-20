package contexts.product.api;

import java.util.UUID;

public class Product {

    public Product(
            UUID id,
            String sku,
            String imgUrl,
            String name,
            String description,
            Price price) {
        this.id = id;
        this.sku = sku;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    private UUID id;
    private String sku;
    private String imgUrl;
    private String name;
    private String description;
    private Price price;

    public UUID getId() {
        return id;
    }
    public String getSku() { return sku; }
    public String getImgUrl() { return imgUrl; }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Price getPrice() { return price; }

}
