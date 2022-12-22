package Accounts;

public abstract class Account {
    String nameType;

    public String getNameType() {
        return nameType;
    }

    private int id;
    private double balance;

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
    public Account(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void withdrawMoney(double amount) {
        balance -= amount;
    }
    public void depositMoney (double amount) {
        balance += amount;
    }
}
