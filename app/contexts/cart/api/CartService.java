package contexts.cart.api;

import javaslang.collection.Set;

import java.util.concurrent.CompletionStage;

public interface CartService {
    CompletionStage emptyCart(String userId);
    CompletionStage updateCartItems(String userId, Set<CartItem> contents);
    CompletionStage<Set<CartItem>> getCartContents(String userId);
}
