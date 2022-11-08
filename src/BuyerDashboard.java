package src;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
import java.util.Scanner;



public class BuyerDashboard {
    public String fileName = "";
    public String userName = "";
    public Scanner scan = new Scanner(System.in);
    public String recipient;
    public String store;
    public int response = 0;




    public BuyerDashboard(String filename, String userName, Scanner scan) {
        this.fileName = filename;
        this.userName = userName;
        this.scan = scan;

    }
    Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);

    public ArrayList<String> readSellers() {
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

    public ArrayList<String> readStores() {
        ArrayList<String> stores = new ArrayList<String>();
        //ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Stores.txt")));
            String line = bfr.readLine();
            while (line != null) {
                stores.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stores;
    }

    public void mainMessage() {
        String print = String.format("Welcome to the Dashboard!\nChoose what you would like to do\n\n" +
                                     "1.) View/Send to stores" +
                                     "2.) Search for a seller" +
                                     "3.) Add a new censor" +
                                     "4.) Export" +
                                     "5.) Exit\n");
        System.out.println(print);
        response = scan.nextInt();
    }

    public void forward() {
        if (response == 1) {
            ArrayList<String> allStores = new ArrayList<String>();
            allStores = readStores();

        }
    }




}
