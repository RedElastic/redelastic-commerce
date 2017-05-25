package infrastructure.ec;

import akka.actor.ActorSystem;
import com.google.inject.Inject;

/**
 * Uses a thread pool for blocking IO.
 * For tuning, look at Little's Law
 * http://www.columbia.edu/~ks20/stochastic-I/stochastic-I-LL.pdf
 * FJ will fork threads if you block.
 */
public class BlockingExecutor extends CustomExecutor {

    @Inject
    public BlockingExecutor(ActorSystem actorSystem) {
        super(actorSystem, "play.blocking-io");
    }

}
