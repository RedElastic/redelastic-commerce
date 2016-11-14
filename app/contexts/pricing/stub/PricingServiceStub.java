package contexts.pricing.stub;

import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import javaslang.control.Option;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javaslang.API.*;
import static javaslang.Predicates.*;

public class PricingServiceStub implements PricingService {

    @Override
    public Option<Price> getPriceForSku(String sku) {
        return Match(sku).option(
            Case(is("1000196120001"), new Price(64, 25)),
            Case(is("2000196120002"), new Price(99, 23)),
            Case(is("3000196120003"), new Price(2, 50)),
            Case(is("4000196120004"), new Price(4, 00)),
            Case(is("6000196120006"), new Price(42, 42))
        );
    }

    @Override
    public Option<Map<String, Price>> getPricesForSkus(List<String> skus) {
        if (skus != null && !skus.isEmpty()) {
            Map<String, Price> prices = skus.stream() // turn list of skus into a stream
                .filter(sku -> getPriceForSku(sku).isDefined()) // filter skus where pricing is not available
                .collect(Collectors.toMap(sku -> sku, sku -> getPriceForSku(sku).get())); // collect down to a map
            return Option.of(prices); // return Some(prices)
        } else {
            return Option.none();
        }
    }

}
