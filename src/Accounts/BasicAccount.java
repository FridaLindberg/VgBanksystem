package Accounts;

public class BasicAccount extends Account {
    String nameType = AccountType.BASICACCOUNT.name();

    @Override
    public String getNameType() {
        return nameType;
    }

    public BasicAccount() {
    }

    public BasicAccount(int id, double balance) {
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
