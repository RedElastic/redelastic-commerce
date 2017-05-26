package core;

import akka.actor.ActorSystem;
import core.cart.api.CartItem;
import core.cart.cluster.CartClusterService;
import javaslang.collection.List;
import org.junit.Test;
import play.Application;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * It might be better to see if cassandra can be included in the test context instead of relying on it externally
 */
public class CartClusterServiceTest {
    Application application = new GuiceApplicationBuilder().build();
    Injector injector = application.injector();
    ActorSystem sys = injector.instanceOf(ActorSystem.class);

    CartClusterService service = new CartClusterService(sys);

    @Test
    public void itShouldAddAndRetrieveContent() throws Exception{
        CartItem item = new CartItem(UUID.randomUUID(), 5, 10.50);

        String userId = "userId-" + UUID.randomUUID(); //don't want to collide with existing test data
        List<CartItem> contents = List.of(item);

        service.updateCartItems(userId, contents).toCompletableFuture().get();
        CompletableFuture<List<CartItem>> result = service.getCartContents(userId).toCompletableFuture();

        List<CartItem> resultContents = result.toCompletableFuture().get();
        assert(resultContents.size() == 1);
        assert(resultContents.get(0) == item);
    }

}
