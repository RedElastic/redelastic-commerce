package core.product.api;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {

    public Product(
            UUID id,
            String imgUrl,
            String name,
            String description,
            Price price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    private UUID id;
    private String imgUrl;
    private String name;
    private String description;
    private Price price;

    public UUID getId() {
        return id;
    }
    public String getImgUrl() { return imgUrl; }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Price getPrice() { return price; }

}
