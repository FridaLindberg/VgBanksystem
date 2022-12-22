package Utility;

import Users.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class History {
    File file = new File("resources/history.txt");

    public void writeToFile(String text, User user) {
        try(BufferedWriter print = new BufferedWriter(new FileWriter(file, true))){

            String textForHistoryTextFile = "User: "+ user.getName() + ".\n" +
                    "Activity: " + text + ".\n" +
                    "Time: " + LocalTime.now().withNano(0) + ".\n" +
                    "Date: " + LocalDate.now() + "\n\n";
            print.write(textForHistoryTextFile);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}