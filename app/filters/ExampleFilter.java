package filters;

import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.inject.*;
import play.mvc.*;
import play.mvc.Http.RequestHeader;

@Singleton
public class ExampleFilter extends Filter {

    private final Executor exec;

    @Inject
    public ExampleFilter(Materializer mat, Executor exec) {
        super(mat);
        this.exec = exec;
    }

    @Override
    public CompletionStage<Result> apply(
        Function<RequestHeader, CompletionStage<Result>> next,
        RequestHeader requestHeader) {

        return next.apply(requestHeader).thenApplyAsync(
            result -> result.withHeader("X-ExampleFilter", "foo"),
            exec
        );
    }

}
