//package contexts;
//
//import akka.actor.ActorSystem;
//import contexts.account.api.Account;
//import contexts.account.live.GithubAccountService;
//import org.junit.Before;
//import org.junit.Test;
//import play.libs.ws.WSClient;
//import play.libs.ws.WS;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CompletionStage;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * This is an integration test -
// * it wouldn't normally be advisable to hit an external http service in a unit test
// * But this gives a good example of play's restful WS api in action.
// */
//public class GithubAccountServiceIntegrationTest {
//
//    GithubAccountService service;
//
//    @Before
//    public void setup() {
//        WSClient ws = WS.newClient(play.api.test.Helpers.testServerPort());
//        service = new GithubAccountService(ws, ActorSystem.create("testSystem"));
//    }
//
//    @Test
//    public void shouldReturnAccountFromGithub() throws Exception {
//        //Invoke the service to get the account asynchronously
//        CompletionStage<Account> cs = service.getAccount("jasongoodwin");
//
//        //We need to have the thread wait for the result in the test or it will succeed regardless of result
//        //We cast the abstract CompletionStage  to the CompletableFuture impl to have access to the get method
//        //Note: It's only appropriate to block w/ get() in test contexts!
//        Account account = ((CompletableFuture<Account>)cs).get(10, TimeUnit.SECONDS);
//
//        //Check the result from github
//        assertEquals("jasongoodwin", account.getUserId());
//        assertEquals("Jason Goodwin", account.getName());
//        assertEquals("https://avatars.githubusercontent.com/u/2845228?v=3", account.getImageUri());
//    }
//}
