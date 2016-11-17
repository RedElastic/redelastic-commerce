package contexts.cart.api;

import java.util.List;

public class Cart {

    private String id;
    private List<LineItem> lineItems;

    public Cart(String id, List<LineItem> lineItems) {
        this.id = id;
        this.lineItems = lineItems;
    }

    public String getId() {
        return id;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
