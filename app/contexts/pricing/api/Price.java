package contexts.pricing.api;

public class Price {

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

}
