package contexts.cart.cluster;

import akka.actor.PoisonPill;
import akka.actor.ReceiveTimeout;
import akka.cluster.sharding.ShardRegion;
import akka.japi.pf.ReceiveBuilder;
import akka.persistence.AbstractPersistentActor;
import contexts.cart.api.CartItem;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Cart extends AbstractPersistentActor {

    List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
        System.out.println("starting a cart! " + self().path());
    }

    @Override
    public String persistenceId() {
        return "Cart-" + self().path().name();
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receiveRecover() {
        return ReceiveBuilder
                .match(EmptyCart.class, msg -> emptyCart())
                .match(UpdateCart.class, msg -> cartItems = msg.getCartItems())
                .build();
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receiveCommand() {
        return ReceiveBuilder
                .match(EmptyCart.class, msg ->
                        persist(msg, (EmptyCart m) ->
                        {
                            emptyCart();
                            sender().tell("done", null);
                        }))
                .match(UpdateCart.class, msg ->
                        persist(msg, (UpdateCart m) ->
                        {
                            setCartItems(m.getCartItems());
                            sender().tell("done", null);
                        }))
                .match(GetContents.class, msg ->
                        sender().tell(cartItems, self())
                )
                .matchEquals(ReceiveTimeout.getInstance(), msg -> passivate())
                .build();
    }


    private void emptyCart() {
        this.cartItems = new ArrayList<>();
    }

    private void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        //Passivation after 120s. TODO make configurable
        context().setReceiveTimeout(Duration.create(120, SECONDS));
    }

    private void passivate() {
        getContext().parent().tell(
                new ShardRegion.Passivate(PoisonPill.getInstance()), self());
    }
}
