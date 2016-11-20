package controllers;

import com.google.inject.Inject;
import contexts.cart.api.Cart;
import contexts.cart.api.CartService;
import contexts.checkout.api.CheckoutService;
import contexts.order.api.Order;
import contexts.order.api.OrderEvent;
import contexts.order.api.OrderService;
import controllers.forms.CheckoutForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.checkout.confirmation;
import views.html.checkout.index;
import websockets.WebSocketsEventBus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CheckoutController extends Controller {

    private final CheckoutService checkoutService;
    private final CartService cartService;
    private final OrderService orderService;
    private final WebSocketsEventBus eventBus;

    private FormFactory formFactory;

    @Inject
    HttpExecutionContext ec; // must have in scope when using CompletionStage<T> inside actions

    @Inject
    public CheckoutController(CheckoutService checkoutService, CartService cartService, OrderService orderService, FormFactory formFactory, WebSocketsEventBus eventBus) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.eventBus = eventBus;
        this.formFactory = formFactory;
    }

    public CompletionStage<Result> index() {
        Form<CheckoutForm> checkoutForm = formFactory.form(CheckoutForm.class);
        CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser(), ec.current());
        return cartFuture.thenApply(cart -> ok(index.render(cart, checkoutForm)));
    }

    public CompletionStage<Result> checkout() {
        Form<CheckoutForm> checkoutForm = formFactory.form(CheckoutForm.class).bindFromRequest();

        if (checkoutForm.hasErrors()) {
            CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser(), ec.current());
            return cartFuture.thenApply(cart -> ok(index.render(cart, checkoutForm)));
        }

        CheckoutForm f = checkoutForm.get();
        Order order = new Order(f.getFirstName(),
                f.getLastName(),
                f.getEmailAddress(),
                f.getShippingOptions(),
                f.getStreet(),
                f.getCity(),
                f.getProvince(),
                f.getPostalCode());

        eventBus.publish(new OrderEvent(order, OrderEvent.EventType.Purchased)); //publish the event to anyone watching the dashboard.

        return CompletableFuture
                .supplyAsync(() -> orderService.saveOrder(order), ec.current())
                .thenApply(id -> {
                    if (id > 0l) {
                        return ok(confirmation.render(order));
                    } else {
                        return internalServerError();
                    }
                });
    }
}