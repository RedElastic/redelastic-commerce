package contexts.account.api;

import java.util.concurrent.CompletionStage;

public interface AccountService {
    public CompletionStage<Account> getAccount(String username);
}
