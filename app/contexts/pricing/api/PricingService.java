package contexts.pricing.api;

import javaslang.control.Option;

import java.util.List;
import java.util.Map;

public interface PricingService {
    Option<Price> getPriceForSku(String sku);
    Option<Map<String, Price>> getPricesForSkus(List<String> skus);
}
