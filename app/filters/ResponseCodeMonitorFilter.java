package filters;

import akka.stream.Materializer;
import contexts.monitoring.api.MonitoringService;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

/**
 * Example of collecting metrics on response code for monitoring.
 * Typically you would want to understand if there are lots of 500s - probably page above a threshold.
 * Could use a lib like Dropwizard Metrics for stats collection, publishing them somewhere (jmx, ganglia, graphite).
 */

@Singleton
public class ResponseCodeMonitorFilter extends Filter {

    private final Executor exec;
    private final MonitoringService monitoringService;

    @Inject
    public ResponseCodeMonitorFilter(Materializer mat, Executor exec, MonitoringService monitoringService) {
        super(mat);
        this.exec = exec;
        this.monitoringService = monitoringService;
    }

    @Override
    public CompletionStage<Result> apply(
            Function<Http.RequestHeader, CompletionStage<Result>> next,
            Http.RequestHeader requestHeader) {

        return next.apply(requestHeader).thenApplyAsync( result -> {
                    monitoringService.incrementResponseCode(result.status());
                    return result;
                },
                exec
        );
    }
}
