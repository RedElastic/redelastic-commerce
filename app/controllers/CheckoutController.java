package controllers;

import com.google.inject.Inject;
import contexts.cart.api.Cart;
import contexts.cart.api.CartService;
import contexts.checkout.api.CheckoutService;
import contexts.order.api.OrderService;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.checkout.index;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CheckoutController extends Controller {

    private final CheckoutService checkoutService;
    private final CartService cartService;
    private final OrderService orderService;

    @Inject
    public CheckoutController(CheckoutService checkoutService, CartService cartService, OrderService orderService) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    public CompletionStage<Result> index() {
        CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser());
        return cartFuture.thenApply(cart -> ok(index.render(cart)));
    }
}