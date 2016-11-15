package ec;

import akka.actor.ActorSystem;
import com.google.inject.Inject;

public class MemoryHungryExecutionContext extends CustomExecutionContext {

    @Inject
    public MemoryHungryExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "play.memory-hungry");
    }

}
