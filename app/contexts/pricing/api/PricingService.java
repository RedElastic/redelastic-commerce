package contexts.pricing.api;

import javaslang.control.Option;

import java.util.List;

public interface PricingService {
    Option<Price> getPriceForSku(String sku);
    Option<List<Price>> getPricesForSku(List<String> skus);
}
