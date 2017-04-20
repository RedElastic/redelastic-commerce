package filters;

import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.mvc.*;
import play.mvc.Http.RequestHeader;

/**
 * Filter adds an Access-Control-Allow-Origin header to requests for cross origin requests.
 * Would be used in an API that is accessed from a browser to avoid tripping CSRF security features on another page.
 */

@Singleton
public class CorsFilter extends Filter {

    private final Executor exec;

    @Inject
    public CorsFilter(Materializer mat, Executor exec) {
        super(mat);
        this.exec = exec;
    }

    @Override
    public CompletionStage<Result> apply(
        Function<RequestHeader, CompletionStage<Result>> next,
        RequestHeader requestHeader) {

        return next.apply(requestHeader).thenApplyAsync(
            result -> result.withHeader("Access-Control-Allow-Origin", "http://localhost:9000"),
            exec
        );
    }

}
