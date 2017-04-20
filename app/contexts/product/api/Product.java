package contexts.product.api;

public class Product {

    public Product(
            Integer id,
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

    private Integer id;
    private String sku;
    private String imgUrl;
    private String name;
    private String description;
    private Price price;

    public Integer getId() {
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
