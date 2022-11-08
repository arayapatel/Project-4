package src;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
import java.util.Scanner;
public class BuyerDashboard {
    public String filename = "";
    public String userName = "";
    public Scanner scan;



    public BuyerDashboard(String filename, String userName, Scanner scan,) {
        this.filename = filename;
        this.userName = userName;
        this.scan = scan;

    }
    Conversation conversation = new Conversation(Scanner scan, String userName, String fileName, String recipient, String store);

    public ArrayList<String> readFile() {
        ArrayList<String> sellers = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Seller.txt")));
            String line = bfr.readLine();
            while (line != null) {
                sellers.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellers;
    }

    public String mainMessage() {
        String print = String.format("Welcome to the Dashboard!\nChoose what you would like to do\n\n" +
                                     "1.) View/Send to stores" +
                                     "2.) Search for a seller" +
                                     "3.) ");
        return print;
    }




}
