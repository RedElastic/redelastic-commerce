package contexts.product.api;

public class Product {

    public Product(
            Integer id,
            String imgUrl,
            String name,
            String description,
            String richContent) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.richContent = richContent;
    }

    public Integer getId() {
        return id;
    }

    public String getImgUrl() { return imgUrl; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRichContent() {
        return richContent;
    }

    private Integer id;
    private String imgUrl;
    private String name;
    private String description;
    private String richContent;

}
