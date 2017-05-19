package core.cart.cluster;

public class EmptyCart implements CartMessage {

    private static final long serialVersionUID = 1L;
    private final String userId;

    public EmptyCart(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
