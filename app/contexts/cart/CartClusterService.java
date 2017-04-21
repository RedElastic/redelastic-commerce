package contexts.cart;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.pattern.PatternsCS;
import contexts.cart.api.CartItem;
import contexts.cart.api.CartService;
import javaslang.collection.Set;

import java.util.concurrent.CompletionStage;

public class CartClusterService implements CartService {

    private final Integer numberOfShards = 100; //don't change after running!!
    private final ActorRef shardRegion;

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

        /**
         * Very basic shutdown hook. Akka 2.5 has improved shutdown semantics but this will do the job for now.
         */

        system.registerOnTermination(() -> {
            shardRegion.tell(ShardRegion.gracefulShutdownInstance(), null);
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e) {
                //whatever
            }

            Cluster cluster = Cluster.get(system);
            cluster.leave(cluster.selfAddress());
        });
    }

    @Override
    public CompletionStage emptyCart(String userId) {
        return PatternsCS.ask(shardRegion, new EmptyCart(userId), 2000);
    }

    @Override
    public CompletionStage updateCartItems(String userId, Set<CartItem> cartItems){
        return PatternsCS.ask(shardRegion, new UpdateCart(userId, cartItems), 2000);
    }

    @Override
    public CompletionStage<Set<CartItem>> getCartContents(String userId){
        CompletionStage result = PatternsCS.ask(shardRegion, new GetContents(userId), 2000);
        return (CompletionStage<Set<CartItem>>) result;
    }
}

