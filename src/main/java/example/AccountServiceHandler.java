package example;

import example.accounts.AccountService;

public class AccountServiceHandler {
    private static final AccountService accountService = new AccountService();

    private AccountServiceHandler() {
    }

    public static AccountService getAccountService() {
        return accountService;
    }
}
