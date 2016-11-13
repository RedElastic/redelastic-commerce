package filters;

import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.inject.*;
import play.mvc.*;
import play.mvc.Http.RequestHeader;

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
            result -> result.withHeader("Access-Control-Allow-Origin", "http://allowed-host.com"),
            exec
        );
    }

}
