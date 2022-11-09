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
   // Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);

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

    ArrayList<String> unsortedSellers = new ArrayList<String>();
    ArrayList<Integer> totalMess = new ArrayList<Integer>();
    public void totalMessages() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Seller.txt")));
            String line = bfr.readLine();
            while (line != null) {
                unsortedSellers.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < unsortedSellers.size(); i++) {
            int index = unsortedSellers.get(i).indexOf(":");
            totalMess.add(Integer.parseInt(unsortedSellers.get(i).substring(index + 1)));
        }
    }
    ArrayList<String> sortedSellers = new ArrayList<String>();
    public void sort() {
        totalMessages();
        Collections.sort(totalMess, Collections.reverseOrder());
        for (int i = 0; i < totalMess.size(); i++) {
            for (int j = 0; j < unsortedSellers.size(); j++) {
                int index = unsortedSellers.get(i).indexOf(":");
                if((Integer.parseInt(unsortedSellers.get(i).substring(index + 1))) == totalMess.get(i)) {
                    int ind = unsortedSellers.get(i).indexOf(";");
                    sortedSellers.add(unsortedSellers.get(i).substring(0, ind));
                }
            }
        }
    }
    public void startMessage() {
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
        startMessage();
        if (response == 1) {
            ArrayList<String> allStores = new ArrayList<String>();
            allStores = readStores();
            System.out.println("Would you like to sort the sellers by their popularity?\n1.) Yes\n2.) No ");
            int resp = scan.nextInt();
            if (resp == 1) {
                sort();
                for (int i = 0; i < sortedSellers.size(); i++) {
                    System.out.println(sortedSellers.get(i) + "\n");
                }
            }else if (resp == 2) {
                for (int i = 0; i < unsortedSellers.size(); i++) {
                    System.out.println(unsortedSellers.get(i) + "\n");
                }
            }
        }
        if (response == 2) {

        }
    }





}
