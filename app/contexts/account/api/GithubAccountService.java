package contexts.account.api;

import akka.actor.ActorSystem;
import akka.pattern.CircuitBreaker;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.Logger;
import play.libs.ws.*;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.CompletionStage;

/**
 * Uses github public user api to get account information
 * eg HTTP GET https://api.github.com/users/jasongoodwin
 *
 * Also wraps the requests in a circuit breaker to demonstrate how to protect
 * the downstream systems from the heard if it's the bottleneck
 */
public class GithubAccountService implements AccountService{
    private static final String USER_API_ENDPOINT = "https://api.github.com/users/";
    private final WSClient ws;
    private final CircuitBreaker breaker;

    @Inject
    public GithubAccountService(WSClient wsClient, ActorSystem system) { //TODO inject configuration for breaker so you can tune it live and open it
        this.ws = wsClient;
        breaker = new CircuitBreaker(system.scheduler(),
                5,                                  //Max number of failures (in a row!) to tolerate before opening breaker
                FiniteDuration.create(1, "second"), //Request timeout: if it takes longer than 1s, consider the req a failure.
                FiniteDuration.create(1, "second"), //Reset: after 1s put into half open
                system.dispatcher()).
                onOpen(() -> Logger.warn("circuit breaker opened!")).           // failure first causes the circuit breaker to open
                onClose(() -> Logger.info("circuit breaker closed!")).          // then, it samples a request
                onHalfOpen(() -> Logger.warn("circuit breaker half opened!"));  //finally once a request succeeds, the breaker closes again
    }

    public CompletionStage<Account> getAccount(String username){
        return ws.url(USER_API_ENDPOINT + username) //This is a builder for a request - can add headers, auth etc here.
                .setMethod("GET")                   //Set http Method.
                .get()                              //execute the request.
                .thenApply(response -> {            //and eventually deserialize the result to an object
                    JsonNode jsonNode = response.asJson();
                    return new Account(
                            jsonNode.get("login").asText(),
                            jsonNode.get("name").asText(),
                            jsonNode.get("avatar_url").asText()
                    );
                });
    }
}
