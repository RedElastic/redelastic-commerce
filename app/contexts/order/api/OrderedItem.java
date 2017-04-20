package contexts.order.api;

public class OrderedItem {
    private String productId;
    private Integer quantity;

    public OrderedItem(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderedItem() { }

    public String getProductId() { return productId; }

    public Integer getQuantity() { return quantity; }
}
