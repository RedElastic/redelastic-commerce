import akka.actor.ActorSystem;
import contexts.pricing.api.PricingService;
import controllers.PricingController;
import org.junit.*;

import static org.junit.Assert.*;
import org.junit.Test;

import play.Application;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.test.WithApplication;
import scala.concurrent.ExecutionContextExecutor;

import java.util.Arrays;

import static play.test.Helpers.*;

public class PricingControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .build();
    }

    @Test
    public void testIndex() {
        Application app = this.provideApplication();
        Injector inj = app.injector();

        ActorSystem as = inj.instanceOf(ActorSystem.class);
        ExecutionContextExecutor ec = inj.instanceOf(ExecutionContextExecutor.class);
        PricingService ps = inj.instanceOf(PricingService.class);

        Result result = new PricingController(as, ec, ps).getPrices(Arrays.asList("1000196120001", "3000196120003"));
        assertEquals(200, result.status());
        assertEquals("application/json", result.contentType().get());
        assertEquals(contentAsString(result), "{\"3000196120003\":{\"dollars\":2,\"cents\":50},\"1000196120001\":{\"dollars\":64,\"cents\":25}}");
    }

}
