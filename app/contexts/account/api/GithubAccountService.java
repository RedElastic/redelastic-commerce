package contexts.account.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.ws.*;

import java.util.concurrent.CompletionStage;

/**
 * Uses github public user api to get account information
 * eg HTTP GET https://api.github.com/users/jasongoodwin
 */
public class GithubAccountService implements AccountService{
    private static final String USER_API_ENDPOINT = "https://api.github.com/users/";
    private final WSClient ws;

    @Inject
    public GithubAccountService(WSClient wsClient) {
        this.ws = wsClient;
    }

    public CompletionStage<Account> getAccount(String username){
        return ws.url(USER_API_ENDPOINT + username) //This is a builder for a request - can add headers, auth etc here.
                .setMethod("GET")                   //Set http Method.
                .get()                              //execute the request.
                .thenApply(response -> {            //and eventually transform the result
                    JsonNode jsonNode = response.asJson();
                    return new Account(
                            jsonNode.get("login").asText(),
                            jsonNode.get("name").asText(),
                            jsonNode.get("avatar_url").asText()
                    );
                });
    }
}
