package contexts.cart.api;

import contexts.pricing.api.Price;
import contexts.product.api.Product;

import java.text.DecimalFormat;

public class LineItem {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    private Product product;
    private int quantity;
    private Price pricePerItem;

    public LineItem(Product product, int quantity, Price pricePerItem) {
        this.product = product;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public Product getProduct() { return product; }

    public int getQuantity() { return quantity; }

    public Price getPricePerItem() { return pricePerItem; }

    public String getFormattedLineItemTotal() {
        double total = pricePerItem.toDouble() * quantity;
        return df2.format(total);
    }

}
