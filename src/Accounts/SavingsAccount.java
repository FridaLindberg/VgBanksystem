package Accounts;

public class SavingsAccount extends Account{
    String nameType = AccountType.SAVINGSACCOUNT.name();


    @Override
    public String getNameType() {
        return nameType;
    }

    public SavingsAccount() {
    }

    public SavingsAccount(int id, double balance) {
        super(id, balance);
    }

    //Overrideade metoder ifall vi vill ha speciella funktioner för olika konton
    @Override
    public void withdrawMoney(double amount) {
        super.withdrawMoney(amount);
    }

    @Override
    public void depositMoney(double amount) {
        super.depositMoney(amount);
    }
}
