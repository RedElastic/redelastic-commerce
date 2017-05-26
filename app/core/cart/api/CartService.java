package core.cart.api;

import javaslang.collection.List;
import java.util.concurrent.CompletionStage;

public interface CartService {
    CompletionStage emptyCart(String userId);
    CompletionStage updateCartItems(String userId, List<CartItem> contents);
    CompletionStage<List<CartItem>> getCartContents(String userId);
}
