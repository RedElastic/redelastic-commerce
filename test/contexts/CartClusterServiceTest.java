package contexts;

import akka.actor.ActorSystem;
import contexts.cart.cluster.CartClusterService;
import contexts.cart.api.CartItem;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CartClusterServiceTest {
    ActorSystem sys = ActorSystem.apply("application");
    CartClusterService service = new CartClusterService(sys);

    @Test
    public void itShouldAddAndRetrieveContent() throws Exception{

        Thread.sleep(30000); //Wait for cluster.

        CartItem item = new CartItem(UUID.randomUUID(), 5, 10.50);

        String userId = "userId";
        Set<CartItem> contents = HashSet.of();
        ((CompletableFuture)service.updateCartItems(userId, contents)).get(100, TimeUnit.SECONDS); //it takes forever to start cluster...

        Set<CartItem> contents1 = (Set<CartItem> )((CompletableFuture) service.getCartContents(userId)).get();

        assert(contents1.contains(item));
    }

}
