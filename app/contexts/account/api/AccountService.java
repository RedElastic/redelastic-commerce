package contexts.account.api;

import java.util.concurrent.CompletionStage;

public interface AccountService {
    CompletionStage<Account> getAccount(String username);
}
