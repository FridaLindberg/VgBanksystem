package Users;

import Accounts.Account;

import java.util.ArrayList;

public class Customer extends User{

    ArrayList<Account> accounts;

    public Customer(String name, String password, String idNumber, ArrayList<Account> accounts) {
        super(name, password, idNumber);
        this.accounts = accounts;

    }




    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


}
