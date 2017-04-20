package controllers;

import com.google.inject.Inject;
import akka.actor.ActorSystem;
import contexts.product.api.ProductService;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;

public class ProductController extends Controller {

    private final ActorSystem actorSystem;
    private final ExecutionContextExecutor ec;
    private final ProductService ps;

    @Inject
    public ProductController(ActorSystem actorSystem, ExecutionContextExecutor ec, ProductService ps) {
        this.actorSystem = actorSystem;
        this.ec = ec;
        this.ps = ps;
    }

    public Result getProducts() {
        return ok("okay!");
    }
}


