import com.google.inject.AbstractModule;
import java.time.Clock;

import contexts.cart.api.CartService;
import contexts.cart.cluster.CartClusterService;
import contexts.order.api.OrderService;
import contexts.order.stub.OrderServiceLive;
import play.libs.akka.AkkaGuiceSupport;

import contexts.monitoring.api.MonitoringService;
import contexts.monitoring.stub.MonitoringServiceStub;
import contexts.product.api.ProductService;
import contexts.product.stub.ProductServiceStub;
import services.ApplicationTimer;

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
        bind(ProductService.class).to(ProductServiceStub.class);
        bind(MonitoringService.class).to(MonitoringServiceStub.class);
        bind(OrderService.class).to(OrderServiceLive.class);
        bind(CartService.class).to(CartClusterService.class);
    }

}
