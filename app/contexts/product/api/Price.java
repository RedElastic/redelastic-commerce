package contexts.product.api;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Price implements Serializable {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public Price(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
    }

    public int getDollars() { return dollars; }
    public int getCents() { return cents; }

    private final int dollars;
    private final int cents;

    public boolean equals(Price price) {
        return this.dollars == price.dollars && this.cents == price.cents;
    }

    @Override
    public String toString() {
        return df2.format(this.toDouble());
    }

    public double toDouble() {
        return dollars + ((double)cents / 100);
    }

}
