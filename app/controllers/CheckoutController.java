package controllers;

import com.google.inject.Inject;
import contexts.cart.api.Cart;
import contexts.cart.api.CartService;
import contexts.checkout.api.CheckoutService;
import contexts.order.api.OrderService;
import controllers.forms.CheckoutForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.checkout.index;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CheckoutController extends Controller {

    private final CheckoutService checkoutService;
    private final CartService cartService;
    private final OrderService orderService;

    private FormFactory formFactory;

    @Inject
    HttpExecutionContext ec; // must have in scope when using CompletionStage<T> inside actions

    @Inject
    public CheckoutController(CheckoutService checkoutService, CartService cartService, OrderService orderService, FormFactory formFactory) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.formFactory = formFactory;
    }

    public CompletionStage<Result> index() {
        Form<CheckoutForm> checkoutForm = formFactory.form(CheckoutForm.class);
        CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser(), ec.current());
        return cartFuture.thenApply(cart -> ok(index.render(cart, checkoutForm)));
    }

    public CompletionStage<Result> checkout() {
        Form<CheckoutForm> checkoutForm = formFactory.form(CheckoutForm.class).bindFromRequest();
        CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser(), ec.current());

        if (checkoutForm.hasErrors()) {
            return cartFuture.thenApply(cart -> badRequest(views.html.checkout.index.render(cart, checkoutForm)));
        }

        flash("success", "Checkout was successful!");

        return cartFuture.thenApply(cart -> ok(index.render(cart, checkoutForm)));
    }

}