package controllers;

import akka.actor.ActorSystem;
import contexts.pricing.api.PricingService;
import contexts.pricing.stub.PricingServiceStub;
import controllers.PricingController;

import static org.junit.Assert.*;
import org.junit.Test;

import play.Application;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import scala.concurrent.ExecutionContextExecutor;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

public class PricingControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(PricingService.class).to(PricingServiceStub.class)) //inject the stub, regardless of the app context
                .build();
    }

    @Test
    public void testGetPrices() {
        Application app = this.provideApplication();
        Injector inj = app.injector();

        PricingService ps = inj.instanceOf(PricingService.class);
        CompletionStage<Result> futureResult = new PricingController(ps).getPrices(Arrays.asList("1000196120001", "3000196120003"));

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method("POST")
                .bodyText("{username: jason")
                .uri("/account");
        System.out.println(request.body().asText());

        try {
            Result result = futureResult.toCompletableFuture().get();
            assertEquals(200, result.status());
            assertEquals("application/json", result.contentType().get());
            assertEquals(contentAsString(result), "{\"3000196120003\":{\"dollars\":2,\"cents\":50},\"1000196120001\":{\"dollars\":64,\"cents\":25}}");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetPrice() throws Exception {
        Application app = this.provideApplication();
        Injector inj = app.injector();

        PricingService ps = inj.instanceOf(PricingService.class);

        CompletionStage<Result> futureResult = new PricingController(ps).getPrice("1000196120001");

        Result result = futureResult.toCompletableFuture().get();
        assertEquals(200, result.status());
        assertEquals("application/json", result.contentType().get());
        assertEquals(contentAsString(result), "{\"dollars\":64,\"cents\":25}");
    }
}
