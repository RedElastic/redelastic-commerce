package ec;

import akka.actor.ActorSystem;
import com.google.inject.Inject;

/**
 * Java Executor for use from guice context for isolating work.
 */

public class MemoryHungryExecutor extends CustomExecutor {

    @Inject
    public MemoryHungryExecutor(ActorSystem actorSystem) {
        super(actorSystem, "play.memory-hungry");
    }

}
