package controllers;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import contexts.cart.CartClusterService;
import play.mvc.Controller;
import play.mvc.Result;

public class CartController extends Controller {
    final private CartClusterService service;

    @Inject
    public CartController(ActorSystem system) {
        service = new CartClusterService(system);
    }

    public Result get(String userId) {
        System.out.println("get cart for " + userId);
        service.emptyCart(userId);
        return ok("hello");
    }
}
