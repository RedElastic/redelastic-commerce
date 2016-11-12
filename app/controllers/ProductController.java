package controllers;

import akka.actor.ActorSystem;
import javax.inject.*;

import contexts.product.api.Product;
import contexts.product.api.ProductService;
import play.mvc.*;
import scala.concurrent.ExecutionContextExecutor;
import views.html.index;

import java.util.List;

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

    public Result index() {
        List<Product> products = ps.lookupProducts();
        return ok(index.render("Coffee Makers", products));
    }

}
