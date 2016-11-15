package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import services.LongRunningComputation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class ApplicationController extends Controller {

    private Executor computationEc; // TODO configure this execution
    private LongRunningComputation lrc;

    @Inject
    public ApplicationController(Executor computationEc, LongRunningComputation lrc) {
        this.computationEc = computationEc;
        this.lrc = lrc;
    }

    public CompletionStage<Result> fibonacci(int limit) {
        CompletionStage<String> fibFuture1 = CompletableFuture.supplyAsync(
                () -> lrc.computeFibonacci(limit), computationEc);
        CompletionStage<String> fibFuture2 = CompletableFuture.supplyAsync(
                () -> lrc.computeFibonacci(limit), computationEc);
        return fibFuture1.thenCompose(f1 -> fibFuture2.thenApply(f2 -> ok("first fib: " + f1 + ", second fib: " + f2)));
    }
}
