package src;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
public class SellerDashboard {
    public Scanner scan;
    public String userName = "";
    public String filename = "";
    public SellerDashboard(Scanner scan, String userName, String fileName) {
        this.scan = scan;
        this.userName = userName;
        this.filename = fileName;
    }
    public ArrayList<String> readFile() {
        //ArrayList<String> buyers = new ArrayList<String>();
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Buyer.txt")));
            String line = bfr.readLine();
            while (line != null) {
                fileContents.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContents;
    }

    public String mainMessage() {
        String print = String.format("Welcom to the Dashboard!");
        return print;
    }


}




