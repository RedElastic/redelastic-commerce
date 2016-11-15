package ec;

import akka.actor.ActorSystem;

class MyCustomExecutor extends CustomExecutionContext {
    // TODO Dependency inject the actorsystem from elsewhere
    public MyCustomExecutor(ActorSystem actorSystem) {
        super(actorSystem, "full.path.to.my-custom-executor");
    }
}
