package services;

import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.api.Application;
import play.api.Play;
import sun.misc.Signal;

import javax.inject.Provider;

@Singleton
public class GracefulShutdown {
    /**
     * We can grab the signal from play, call coordinated shutdown on akka. That should bring down the house.
     * This should be refactored toward somewhere more general. Previously you'd need to manually do the graceful exit
     * But now Akka takes care of it.
     * This is probably not the best approach - it should allow graceful exit of the cluster in Docker, mesos, etc.
     * Apache Aurora will call an endpoint before shutting down but in DCOS we don't have any hooks apart from the SIGTERM.
     * We have to use it to try to get out of the cluster gracefully to speed handoff on scale down/redeploy.
     *
     * You'll want to pull it out of the loadbalancer immediately - this won't sit around and drain.
     * After graceful shutdown, play is stopped. Marathon-lb listens to marathon's lifecycle event bus so it
     * can pull a node out of the load balancer immediately. It would be preferred to the l4lb (minuteman)
     * https://github.com/mesosphere/marathon-lb
     */

    @Inject
    public GracefulShutdown(ActorSystem system, Provider<Application> applicationProvider) {
        System.out.println("starting");
        Signal.handle(new Signal("TERM"), new sun.misc.SignalHandler() {
            @Override
            public void handle(Signal signal) {
                System.out.println("RECEIVED TERM!");
                CoordinatedShutdown.get(system).runAll().thenRun(() -> {
                    Play.stop(applicationProvider.get());
                    System.exit(0);
                });
            }
        });
    }
}
