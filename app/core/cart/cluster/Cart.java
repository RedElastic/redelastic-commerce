package core.cart.cluster;

import akka.actor.PoisonPill;
import akka.actor.ReceiveTimeout;
import akka.cluster.sharding.ShardRegion;
import akka.persistence.AbstractPersistentActor;
import core.cart.api.CartItem;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.Collections;

import javaslang.collection.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * This is technically CommandSourcing because we persist the commands themselves.
 * Separate events should be stored instead ("CartEmpties", "CartUpdated")
 * Otherwise side-effects would be re-applied. There are no side effects outside of this class however.
 * TODO switch to event sourcing model
 */

public class Cart extends AbstractPersistentActor {

    List<CartItem> cartItems = List.empty(); //We use an immutable list with a non-final reference so we can send it at any time

    public Cart() {
        System.out.println("starting a cart! " + self().path());
    }

    @Override
    public String persistenceId() {
        return "Cart-" + self().path().name();
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(EmptyCart.class, msg -> emptyCart())
                .match(UpdateCart.class, msg -> cartItems = msg.getCartItems())
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EmptyCart.class, msg ->
                        persist(msg, (EmptyCart m) ->
                        {
                            emptyCart();
                            sender().tell("done", null);
                        }))
                .match(UpdateCart.class, msg -> {
                    persist(msg, (UpdateCart m) ->
                    {
                        setCartItems(m.getCartItems());
                        sender().tell("done", null);
                    });
                })

                .match(GetContents.class, msg -> {
                    sender().tell(cartItems, self());
                })
                .matchEquals(ReceiveTimeout.getInstance(), msg -> passivate())
                .matchAny( msg -> System.out.println("received unknown message: " + msg))
                .build();
    }


    private void emptyCart() {
        this.cartItems = List.empty();
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

    /**
     * This will sleep the actor until it needs to be re-awoken.
     */
    private void passivate() {
        getContext().parent().tell(
                new ShardRegion.Passivate(PoisonPill.getInstance()), self());
    }
}
