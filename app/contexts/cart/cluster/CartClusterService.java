package contexts.cart.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.PatternsCS;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import contexts.cart.api.CartItem;
import contexts.cart.api.CartService;
import play.api.Application;
import play.api.Play;
import scala.concurrent.duration.FiniteDuration;
import sun.misc.Signal;

import javax.inject.Provider;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Singleton
public class CartClusterService implements CartService {

    private final Integer numberOfShards = 100; //don't change after running!! Hardcoded so it's NOT configurable.
    private final ActorRef shardRegion;
    private final LoggingAdapter log;
    @Inject
    public CartClusterService(ActorSystem system, Provider<Application> applicationProvider) {
        log = Logging.getLogger(system, this);

        ShardRegion.MessageExtractor extractor = new ShardRegion.MessageExtractor() {
            @Override
            public String entityId(Object message) {
                if(message instanceof CartMessage){
                    return ((CartMessage) message).getUserId();
                } else {
                    throw new Error("message does not implement CartMessage!");
                }
            }

            /**
             * See also ShardRegion.HashCodeMessageExtractor
             * @param message
             * @return
             */
            @Override
            public String shardId(Object message) {
                if(message instanceof CartMessage){
                    String shardId = String.valueOf(((CartMessage) message).getUserId().hashCode() % numberOfShards);
                    return shardId;
                } else {
                    throw new Error("message does not implement CartMessage!");
                }
            }

            @Override
            public Object entityMessage(Object message) {
                System.out.println("entity message " + message);
                return message;
            }
        };

        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
         shardRegion = ClusterSharding.get(system).start("RE-Cart",
                Props.create(Cart.class), settings, extractor);

    }

    @Override
    public CompletionStage emptyCart(String userId) {
        return PatternsCS.ask(shardRegion, new EmptyCart(userId), 2000);
    }

    @Override
    public CompletionStage updateCartItems(String userId, List<CartItem> cartItems){
        return PatternsCS.ask(shardRegion, new UpdateCart(userId, cartItems), 2000);
    }

    @Override
    public CompletionStage<List<CartItem>> getCartContents(String userId){
        CompletionStage result = PatternsCS.ask(shardRegion, new GetContents(userId), 2000);
        return (CompletionStage<List<CartItem>>) result;
    }
}

