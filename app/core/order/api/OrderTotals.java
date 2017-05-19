package core.order.api;

public class OrderTotals {
    private final Double subtotal;
    private final Double taxes;
    private final Double total;

    public OrderTotals(Double subtotal, Double taxes, Double total) {
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
    }

    public Double getSubtotal() { return subtotal; }
    public Double getTaxes() { return taxes; }
    public Double getTotal() { return total; }
}
