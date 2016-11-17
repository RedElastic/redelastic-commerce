package contexts.cart.api;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private static DecimalFormat df2 = new DecimalFormat(".##");

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

    public String getFormattedCartTotal() {
        double total = lineItems.stream() // turn list of skus into a stream
                .map(item -> item.getPricePerItem().toDouble())
                .reduce(
                        0.0,
                        (a, b) -> a + b);

        return df2.format(total);
    }
}
