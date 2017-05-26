package core.cart.cluster;

import core.cart.api.CartItem;

import javaslang.collection.List;

public class UpdateCart implements CartMessage {
    private static final long serialVersionUID = 1L;
    private final List<CartItem> cartItems;
    private final String cartId;

    public UpdateCart(String cartId, List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.cartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String getUserId() {
        return cartId;
    }
}
