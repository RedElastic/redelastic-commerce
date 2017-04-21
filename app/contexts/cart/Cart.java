package contexts.cart;

import akka.persistence.AbstractPersistentActor;
import contexts.product.api.Product;
import javaslang.Tuple2;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

public class Cart extends AbstractPersistentActor {

    private final String userId;
    Set<Tuple2<Product, Integer>> contents;

    public Cart(String userId) {
        this.userId = userId;
    }

    @Override
    public String persistenceId() {
        return userId;
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(EmptyCart.class, msg -> emptyCart())
                .match(UpdateCart.class, msg -> contents = msg.getContents())
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EmptyCart.class, msg -> {
                    persist(msg, (EmptyCart m) -> emptyCart());
                })
                .match(UpdateCart.class, msg -> {
                    persist(msg, (UpdateCart m) -> setContents(m.getContents()));
                })
                .match(GetContents.class, msg -> {
                    sender().tell(contents, self());
                })
                .build();
    }

    private void emptyCart() {
        this.contents = HashSet.empty();
    }

    private void setContents(Set<Tuple2<Product, Integer>> contents) {
        this.contents = contents;
    }
}
