import static org.junit.Assert.*;

import com.google.inject.Inject;
import contexts.pricing.api.Price;
import contexts.pricing.api.PricingService;
import contexts.pricing.stub.PricingServiceStub;
import javaslang.control.Option;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static play.inject.Bindings.bind;

public abstract class PricingServiceTest extends WithApplication {

    @Override
    protected Application provideApplication()
    {
        return new GuiceApplicationBuilder()
            .overrides(bind(PricingService.class).to(PricingServiceStub.class))
            .in(Mode.TEST)
            .build();
    }

    @Inject
    PricingService ps;

    @Test
    public void testSingleSkuPrice() {
        Option<Price> price = ps.getPriceForSku("1000196120001");
        assertEquals(true, price.get().equals(new Price(64, 25)));
    }
}