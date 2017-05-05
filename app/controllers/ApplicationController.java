package controllers;

import com.google.inject.Inject;
import infrastructure.ec.MemoryHungryExecutor;
import play.mvc.Controller;
import play.mvc.Result;
import infrastructure.LongRunningComputation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ApplicationController extends Controller {

    private MemoryHungryExecutor ec;
    private LongRunningComputation lrc;

    @Inject
    public ApplicationController(MemoryHungryExecutor ec, LongRunningComputation lrc) {
        this.lrc = lrc;
        this.ec = ec;
    }

    public CompletionStage<Result> fibonacci(int limit) {
        CompletionStage<String> fibFuture1 = CompletableFuture.supplyAsync(
                () -> lrc.computeFibonacci(limit), ec);
        CompletionStage<String> fibFuture2 = CompletableFuture.supplyAsync(
                () -> lrc.computeFibonacci(limit), ec);
        return fibFuture1.thenCompose(f1 -> fibFuture2.thenApply(f2 -> ok("first fib: " + f1 + ", second fib: " + f2)));
    }
}
