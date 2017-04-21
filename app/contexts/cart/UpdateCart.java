package contexts.cart;

import contexts.cart.api.CartItem;
import javaslang.collection.Set;

public class UpdateCart implements CartMessage {
    private static final long serialVersionUID = 1L;
    private final Set<CartItem> cartItems;
    private final String cartId;

    public UpdateCart(String cartId, Set<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.cartId = cartId;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String getUserId() {
        return cartId;
    }
}
