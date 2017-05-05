package infrastructure.ec;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContext;

import java.util.concurrent.Executor;

/**
 * Abstract Java Executor for use from Guice context for isolating work
 */
abstract class CustomExecutor implements Executor {
    private final ExecutionContext executionContext;

    public CustomExecutor(ActorSystem actorSystem, String name) {
        this.executionContext = actorSystem.dispatchers().lookup(name);
    }

    @Override
    public void execute(Runnable command) {
        executionContext.execute(command);
    }
}