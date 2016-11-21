package controllers;

import javax.inject.*;

import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import play.libs.Json;
import play.mvc.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PricingController extends Controller {

    private final PricingService ps;

    @Inject
    public PricingController(PricingService ps) {
        this.ps = ps;
    }

    public CompletionStage<Result> getPrices(List<String> skus) {
        return CompletableFuture
            .supplyAsync(() -> ps.getPricesForSkus(skus))
            .thenApply(maybePrices -> {
                if (maybePrices.isDefined()) {
                    Map<String, Price> prices = maybePrices.get();
                    return ok(Json.toJson(prices));
                } else {
                    return ok();
                }
            }
        );
    }

    public CompletionStage<Result> getPrice(String sku) {
        return CompletableFuture
            .supplyAsync(() -> ps.getPriceForSku(sku))
            .thenApply(maybePrice -> {
                    if (maybePrice.isDefined()) {
                        Price price = maybePrice.get();
                        return ok(Json.toJson(price));
                    } else {
                        return ok();
                    }
                }
            );
    }

}
