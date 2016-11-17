package contexts;

import static org.junit.Assert.*;

import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import contexts.pricing.stub.PricingServiceStub;
import javaslang.control.Option;
import org.junit.Test;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;

import java.util.Arrays;
import java.util.Map;

import static play.inject.Bindings.bind;

public class PricingServiceTest {
    @Test
    public void testLookupSinglePrice() {
        Injector injector = new GuiceInjectorBuilder()
            .overrides(bind(PricingService.class).to(PricingServiceStub.class))
            .injector();

        PricingService ps = injector.instanceOf(PricingService.class);

        Option<Price> price = ps.getPriceForSku("1000196120001");
        assertEquals(true, price.get().equals(new Price(64, 25)));
    }

    @Test
    public void testLookupMultiplePrices() {
        Injector injector = new GuiceInjectorBuilder()
                .overrides(bind(PricingService.class).to(PricingServiceStub.class))
                .injector();

        PricingService ps = injector.instanceOf(PricingService.class);

        Option<Map<String, Price>> maybePrices = ps.getPricesForSkus(Arrays.asList("1000196120001","3000196120003"));
        Map<String, Price> prices = maybePrices.get();

        assertTrue(maybePrices.isDefined()); // ensure a list of prices is returned
        assertEquals(2, prices.size()); // ensure only two prices are returned
        assertTrue(prices.containsKey("1000196120001")); // make sure both keys are present
        assertTrue(prices.containsKey("3000196120003"));
        assertTrue(new Price(64, 25).equals(prices.get("1000196120001"))); // check the price
        assertTrue(new Price(2, 50).equals(prices.get("3000196120003"))); // check the price
    }
}