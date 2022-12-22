package Utility;

import Accounts.Account;
import Accounts.AccountFactory;
import Accounts.AccountType;
import Database.Database;
import Users.Customer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utility {

    History history = new History();
    Random random = new Random();

    Database database = Database.getDatabase();

    public int inputInt(String text) {
        while (true) {
            Scanner scan;
            System.out.println(text);
            try {
                scan = new Scanner(System.in);
                return scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Förväntade mig ett nummer");
            } catch (NumberFormatException e) {
                System.out.println("Inte nummer");
            }
        }
    }

    public void sleep(int number){
        try{
            Thread.sleep(number);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void transfer(Customer customer){
        boolean found = false;
        int value = inputInt("Hur mycket vill du överföra?");
        if(value > 0) {
            System.out.println("Från vilket konto?");
            for (int i = 0; i < customer.getAccounts().size(); i++) {
                System.out.println(i + 1 + ". Konto: " + customer.getAccounts().get(i).getId() + " Balance: " + customer.getAccounts().get(i).getBalance() + " kr");
            }
            int konto = inputInt("Svara med siffran som stämmer överens med Kontot") - 1;
            if(konto < customer.getAccounts().size()) {
                int number = inputInt("Till vilket konto nummer? (sexsiffrigt nummer)");
                if (customer.getAccounts().get(konto).getBalance() - value >= 0) {
                    for (int i = 0; i < database.getCustomers().size(); i++) {
                        for (int j = 0; j < database.getCustomers().get(i).getAccounts().size(); j++) {
                            if (database.getCustomers().get(i).getAccounts().get(j).getId() == number) {
                                customer.getAccounts().get(konto).withdrawMoney(value);
                                database.getCustomers().get(i).getAccounts().get(j).depositMoney(value);
                                System.out.println(value + " kr fördes över till konto " + number);
                                history.writeToFile("Transfer " + value + " kr from Account " + customer.getAccounts().get(konto).getId() + " to Account " + number, customer);
                                found = true;
                                database.updateCustomerTextFile();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        System.out.println("Kontot finns ej.");
                    }
                } else {
                    System.out.println("Du har för lite pengar på kontot");
                }
            } else {
                System.out.println("Felaktigt konto");
            }
        } else {
            System.out.println("Du kan ej överföra mindre än 0 kr.");
        }
    }

    public void deposit(Customer customer) {
        int value = inputInt("Hur mycket vill du lägga in?");
        if(value > 0) {
            System.out.println("Till vilket konto?");
            for (int i = 0; i < customer.getAccounts().size(); i++) {
                System.out.println(i + 1 + ". Konto: " + customer.getAccounts().get(i).getId() + " Balance: " + customer.getAccounts().get(i).getBalance() + " kr");
            }
            int konto = inputInt("Svara med siffran som stämmer överens med Kontot") - 1;
            if(konto < customer.getAccounts().size()) {
                customer.getAccounts().get(konto).depositMoney(value);
                System.out.println(value + " kr sattes in på kontot " + customer.getAccounts().get(konto).getId() + "\n");
                history.writeToFile("Deposit " + value + " kr to Account " + customer.getAccounts().get(konto).getId(), customer);
                sleep(2000);
                database.updateCustomerTextFile();
            } else {
                System.out.println("Felaktigt konto");
            }
        } else {
            System.out.println("Du kan ej sätta in mindre än 0 kr");
        }
    }

    public void checkAccount(Customer customer) {
        StringBuilder accounts = new StringBuilder();
        for (int i = 0; i < customer.getAccounts().size() ; i++) {
            accounts.append("Konto: " + customer.getAccounts().get(i).getNameType() + "\nKontonummer: " + customer.getAccounts().get(i).getId() + "\nSaldo: " + customer.getAccounts().get(i).getBalance() + " kr\n\n");
        }
        if(accounts.isEmpty()){
            System.out.println("\n" + customer.getName() + "\n");
        } else {
            System.out.println("\n" + customer.getName() + "\n\n" + accounts);
        }
        sleep(2000);
    }

    public void withdraw(Customer customer) {
        int value = inputInt("Hur mycket vill du ta ut?");
        if(value > 0) {
            System.out.println("Från vilket konto?");
            for (int i = 0; i < customer.getAccounts().size(); i++) {
                System.out.println(i + 1 + ". Konto: " + customer.getAccounts().get(i).getId() + " Balance: " + customer.getAccounts().get(i).getBalance() + " kr");
            }
            int konto = inputInt("Svara med siffran som stämmer överens med Kontot") - 1;
            if(konto < customer.getAccounts().size()) {
                if (customer.getAccounts().get(konto).getBalance() - value >= 0) {
                    customer.getAccounts().get(konto).withdrawMoney(value);
                    System.out.println(value + " kr togs ut från kontot " + customer.getAccounts().get(konto).getId() + "\n");
                    history.writeToFile("Withdraw " + value + " kr from Account " + customer.getAccounts().get(konto).getId(), customer);
                } else {
                    System.out.println("Du har för lite pengar på kontot");
                }
                database.updateCustomerTextFile();
            } else {
                System.out.println("Felaktigt konto");
            }
        } else {
            System.out.println("Du kan ej ta ut mindre än 0 kr");
        }
        sleep(2000);
    }

    public int createRandomNumber(){
        int number;
        boolean checkIfNumberExists;
        do {
            checkIfNumberExists = false;
            number = random.nextInt(999999) + 100000;
            for (int i = 0; i < database.getCustomers().size(); i++) {
                for (int j = 0; j < database.getCustomers().get(i).getAccounts().size(); j++) {
                    if (database.getCustomers().get(i).getAccounts().get(j).getId() == number) {
                        checkIfNumberExists = true;
                        break;
                    }
                }
            }
        } while (checkIfNumberExists);
        return number;
    }

    public void createNewAccount(Customer customer) {
        System.out.println("Vilkets sorts konto vill du skapa?");
        for (int i = 0; i < AccountType.values().length; i++) {
            System.out.println(i+1 + ". " + AccountType.values()[i]);
        }
        int number = inputInt("svara med rätt siffra") - 1;
        if(number <= AccountType.values().length) {
            customer.getAccounts().add(AccountFactory.getAccount(AccountType.values()[number]));
            int id = createRandomNumber();
            customer.getAccounts().get(customer.getAccounts().size()-1).setId(id);
            history.writeToFile("Created new " + AccountType.values()[number] + " with number: " + id , customer);
            database.updateCustomerTextFile();
        } else {
            System.out.println("felaktigt siffra");
        }
    }
    public void deleteAccount(Customer customer) {
        boolean found = false;
        System.out.println("Vilket konto vill du radera?\n");
        for (int i = 0; i < customer.getAccounts().size(); i++) {
            System.out.println(i + 1 + ". Konto: " + customer.getAccounts().get(i).getId() + " Balance: " + customer.getAccounts().get(i).getBalance() + " kr");
        }
        int account = inputInt("Skriv det sexsiffriga kontonumret");
        for (int i = 0; i < customer.getAccounts().size(); i++) {
            if (customer.getAccounts().get(i).getId() == account) {
                customer.getAccounts().remove(i);
                found = true;
                database.updateCustomerTextFile();
            }
        }
        if (!found) {
            System.out.println("Felaktigt kontonummer");
        }
    }
    public void addCustomer() {
        System.out.println("Skriv in ditt namn");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Skriv in ditt lösenord");
        String password = scan.nextLine();
        System.out.println("Skriv in ditt personnummer");
        String idNumber = scan.nextLine();
        ArrayList<Account> temp = new ArrayList<>();
        Account temp2 = AccountFactory.getAccount(AccountType.BASICACCOUNT);
        temp2.setId(createRandomNumber());
        temp2.setBalance(0);
        temp.add(temp2);
        database.getCustomers().add(new Customer(name, password, idNumber, temp));
        database.updateCustomerTextFile();
    }

    public boolean deleteCustomer(Customer customer) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Vill du verkligen radera ditt användarkonto? (j/n)");
        if(scan.next().equalsIgnoreCase("j")) {
            database.getCustomers().remove(customer);
            database.updateCustomerTextFile();
            return false;
        }
        else if (scan.next().equals("n")) {
            System.out.println("Du är fortfarande vår kund!");
        }
        else {
            System.out.println("Skriv j eller n");
        }
        return true;
    }
}
