package contexts.product.api;

public class Product {

    public Product(Integer id,
                   String sku,
                   String name,
                   String richContent) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.richContent = richContent;
    }

    public Integer getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getRichContent() {
        return richContent;
    }

    private Integer id;
    private String sku;
    private String name;
    private String richContent;

}
