package Accounts;

public class AccountFactory {
    public static Account getAccount(AccountType accountType) {
        switch (accountType) {
            case SAVINGSACCOUNT:
                return new SavingsAccount();
            case BASICACCOUNT:
                return new BasicAccount();
            default:
                return null;
        }
    }
}
