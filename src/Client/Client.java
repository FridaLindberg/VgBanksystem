package Client;

import Accounts.Account;
import Accounts.AccountFactory;
import Accounts.AccountType;
import Database.Database;
import Users.*;
import Utility.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    LocalDate today = LocalDate.now();
    Utility utility = new Utility();
    FAQ faq = new FAQ();

    Database database = Database.getDatabase();

    void Program() {

        System.out.println("\nVälkommen till Bank Systemet");
        System.out.println("Dagens Datum: " + today + "\n");
        System.out.println("Kontaktinformation:\nEmail: bank@bank.com\nAdress: Bankgatan 1, Bankstaden.\n");
        while (true) {
            int answer = utility.inputInt("""
                    Vad vill du göra?
                    1. Logga in
                    2. Skapa ny användare
                    3. FAQ
                    4. Skicka in en fråga
                    5. Avsluta Programmet""");
            switch (answer) {
                case (1) -> {
                    System.out.println("\nSkriv in personnummer:");
                    Scanner scan = new Scanner(System.in);
                    String name = scan.nextLine();
                    boolean found = false;
                    for (int i = 0; i < database.getCustomers().size(); i++) {
                        if (name.equalsIgnoreCase(database.getCustomers().get(i).getNumber())) {
                            System.out.println("Skriv in lösenord:");
                            String password = scan.nextLine();
                            found = true;
                            if (password.equals(database.getCustomers().get(i).getPassword())) {
                                login(database.getCustomers().get(i));
                            } else {
                                System.out.println("Felaktigt lösenord.\n");
                            }
                            break;
                        }
                    }
                    for (int i = 0; i < database.getAdmins().size(); i++) {
                        if (name.equalsIgnoreCase(database.getAdmins().get(i).getNumber())) {
                            System.out.println("Skriv in lösenord:");
                            String password = scan.nextLine();
                            found = true;
                            if (password.equals(database.getAdmins().get(i).getPassword())) {
                                loginAdmin(database.getAdmins().get(i));
                            } else {
                                System.out.println("Felaktigt lösenord.\n");
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Användaren hittades inte\n");
                    }
                }
                case (2) -> {
                    utility.addCustomer();
                    utility.sleep(1000);
                }
                case (3) -> {
                    faq.readingFAQ();
                    utility.sleep(1000);
                }
                case (4) -> {
                    sendQuestion();
                }
                case (5) -> System.exit(0);
                default -> System.out.println("Felaktigt nummer");
            }
        }
    }

    private void login (Customer customer){
        boolean startLoop = true;
        do {
            int answer = utility.inputInt("Välkommen " + customer.getName() +
                    "\n1. Överföra pengar\n2. Sätta in pengar\n3. Ta ut pengar\n4. Kolla dina konton\n" +
                    "5. Skapa nytt bankkonto\n6. Radera bankkonto\n7. Radera ditt användarkonto\n8. Logga ut");
                switch (answer) {
                    case (1) -> utility.transfer(customer);
                    case (2) -> utility.deposit(customer);
                    case (3) -> utility.withdraw(customer);
                    case (4) -> utility.checkAccount(customer);
                    case (5) -> utility.createNewAccount(customer);
                    case (6) -> utility.deleteAccount(customer);
                    case (7) -> startLoop = utility.deleteCustomer(customer);
                    case (8) -> startLoop = false;
                    default -> System.out.println("Felaktigt nummer");
                }
            }while(startLoop);
    }

    private void loginAdmin (Admin admin){
        boolean startLoop = true;
        do {
            int answer = utility.inputInt("Välkommen " + admin.getName() +
                    "\n1. Uppdatera FAQ\n2. Logga ut");
            switch (answer) {
                case (1) -> faq.writingFAQ();
                case (2) -> startLoop = false;
                default -> System.out.println("Felaktigt nummer");
            }
        }while(startLoop);
    }


    public void sendQuestion(){
        Scanner scan = new Scanner(System.in);
        System.out.println("\nSkriv din fråga:");
        String question = scan.nextLine();

        try(PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("resources/questionsFromCustomers.txt", true)));){
            write.append(System.lineSeparator() + question + "\n");
            System.out.println("Din fråga är skickad!\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.Program();
    }
}
