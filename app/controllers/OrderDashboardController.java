package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.mvc.Controller;
import play.mvc.Result;
import com.google.inject.Inject;

public class OrderDashboardController extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public OrderDashboardController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result index() {
        return ok("okay!");
    }


}
