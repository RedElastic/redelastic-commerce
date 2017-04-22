package contexts.cart.api;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface CartService {
    CompletionStage emptyCart(String userId);
    CompletionStage updateCartItems(String userId, List<CartItem> contents);
    CompletionStage<List<CartItem>> getCartContents(String userId);
}
