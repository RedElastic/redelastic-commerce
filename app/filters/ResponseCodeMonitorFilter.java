package filters;

import akka.stream.Materializer;
import contexts.product.api.MonitoringService;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

/**
 * Retrieve a User-session from an external kv store like redis.
 * To maintain a stateless application, we'll store the session in somewhere external.
 * (This is similar to what Rails does)
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
