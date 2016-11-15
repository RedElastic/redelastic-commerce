import com.google.inject.AbstractModule;
import java.time.Clock;

import play.libs.akka.AkkaGuiceSupport;

import contexts.monitoring.api.MonitoringService;
import contexts.monitoring.stub.MonitoringServiceStub;
import contexts.pricing.api.PricingService;
import contexts.pricing.stub.PricingServiceStub;
import contexts.product.api.ProductService;
import contexts.product.stub.ProductServiceStub;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule implements AkkaGuiceSupport {

    @Override
    public void configure() {
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        bind(ApplicationTimer.class).asEagerSingleton();
        bind(Counter.class).to(AtomicCounter.class);

        // Bind all services to stubs for testing
        bind(ProductService.class).to(ProductServiceStub.class);
        bind(PricingService.class).to(PricingServiceStub.class);
        bind(MonitoringService.class).to(MonitoringServiceStub.class);

    }

}
