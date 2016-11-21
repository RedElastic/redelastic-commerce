package controllers;

import com.google.inject.Inject;
import contexts.account.api.AccountService;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AccountController extends Controller {

    private final AccountService accountService;
    private final FormFactory formFactory;

    @Inject
    public AccountController(AccountService accountService, FormFactory formFactory) {
        this.accountService = accountService;
        this.formFactory = formFactory;
    }

    /**
     * Takes the userId and creates a session if a GitHub account exists
     *
     * @return
     */
    public CompletionStage<Result> login() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String userId = requestData.get("user");

        Http.Session session = session(); //we need to get the session here as we want to access it from a closure

        return accountService.getAccount(userId).handle((account, t) -> {
            if (account != null) {
                session.put("userId", account.getUserId());
                return ok("added"); // At this point you'll see the id in a cookie (TODO redirect)
            } else {
                return unauthorized("user not found");
            }
        });
    }

    public CompletionStage<Result> account() {
        String userId = session("userId");

        if (userId == null) {
            return CompletableFuture.completedFuture(ok(views.html.login.render()));
        } else {
            return accountService.getAccount(userId).thenApply(account -> ok(Json.toJson(account)));
        }
    }
}
