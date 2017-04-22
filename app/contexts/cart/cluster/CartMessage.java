package contexts.cart.cluster;

import java.io.Serializable;

/**
 * Commands or queries for Cart.
 * Must have the userId to locate thne shard.
 */

public interface CartMessage extends Serializable {
    public String getUserId();
}