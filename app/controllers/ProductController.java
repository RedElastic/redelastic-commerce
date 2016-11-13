/**
 Copyright 2016 RedElastic Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

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
