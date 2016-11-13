package contexts.pricing.stub;

import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import javaslang.control.Option;

import java.util.List;
import java.util.stream.Collectors;

import static javaslang.API.*;
import static javaslang.Predicates.*;

public class PricingServiceStub implements PricingService {

    @Override
    public Option<Price> getPriceForSku(String sku) {
        return Match(sku).option(
            Case(is("1000196120001"), new Price(64, 25)),
            Case(is("2000196120002"), new Price(99, 23)),
            Case(is("3000196120003"), new Price(99, 23)),
            Case(is("4000196120004"), new Price(99, 23)),
            Case(is("5000196120005"), new Price(99, 23))
        );
    }

    @Override
    public Option<List<Price>> getPricesForSku(List<String> skus) {
        if (skus != null && !skus.isEmpty()) {
            List<Price> prices = skus.stream()
                .map(s -> getPriceForSku(s))
                .filter(maybeSku -> maybeSku.isDefined())
                .map(optionOfSku -> optionOfSku.get())
                .collect(Collectors.toList());
            return Option.of(prices);
        } else {
            return Option.none();
        }
    }

}
