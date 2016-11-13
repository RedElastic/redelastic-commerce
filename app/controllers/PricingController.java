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

import com.fasterxml.jackson.databind.JsonNode;
import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import javaslang.control.Option;
import play.libs.Json;
import play.mvc.*;
import scala.concurrent.ExecutionContextExecutor;

import java.util.List;
import java.util.Map;

public class PricingController extends Controller {

    private final ActorSystem actorSystem;
    private final ExecutionContextExecutor ec;
    private final PricingService ps;

    @Inject
    public PricingController(ActorSystem actorSystem, ExecutionContextExecutor ec, PricingService ps) {
        this.actorSystem = actorSystem;
        this.ec = ec;
        this.ps = ps;
    }

    public Result getPrices(List<String> skus) {
        Option<Map<String, Price>> maybePrices = ps.getPricesForSkus(skus);
        if (maybePrices.isDefined()) {
            Map<String, Price> prices = maybePrices.get();
            return ok(Json.toJson(prices));
        } else {
            return ok();
        }
    }

}
