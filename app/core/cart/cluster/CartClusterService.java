package core.cart.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.pattern.PatternsCS;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.cart.api.CartItem;
import core.cart.api.CartService;

import javaslang.collection.List;

import java.util.concurrent.CompletionStage;

@Singleton
public class CartClusterService implements CartService {

    private final Integer numberOfShards = 100; //don't change after running!! Hardcoded so it's NOT configurable.
    private final ActorRef shardRegion;

    @Inject
    public CartClusterService(ActorSystem system) {

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
        return PatternsCS.ask(shardRegion, new EmptyCart(userId), 10000); //TODO make configurable timeouts, breakers
    }

    @Override
    public CompletionStage updateCartItems(String userId, List<CartItem> cartItems){
        return PatternsCS.ask(shardRegion, new UpdateCart(userId, cartItems), 10000); //TODO make configurable timeouts, breakers
    }

    @Override
    public CompletionStage<List<CartItem>> getCartContents(String userId){
        CompletionStage result = PatternsCS.ask(shardRegion, new GetContents(userId), 10000); //TODO make configurable timeouts, breakers
        return (CompletionStage<List<CartItem>>) result;
    }
}

