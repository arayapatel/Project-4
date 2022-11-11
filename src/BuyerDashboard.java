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
    public String recipient = "";
    public String store = "";
    public int response = 0;

    public BuyerDashboard(String filename, String userName, Scanner scan) {
        this.fileName = filename;
        this.userName = userName;
        this.scan = scan;

    }
   // Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
    ArrayList<String> stores = new ArrayList<String>();
    public void readStores() {

        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Seller.txt")));
            String line = bfr.readLine();
            while (line != null) {
                fileContents.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < fileContents.size(); i++) {
            int startIndex = fileContents.get(i).indexOf(";");
            int endIndex = fileContents.get(i).indexOf(":");
            stores.add(unsortedSellers.get(i).substring(startIndex + 2, endIndex));
        }
        //return null;
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
    ArrayList<String> sortedStores = new ArrayList<String>();
    public void sort() {
        totalMessages();
        Collections.sort(totalMess, Collections.reverseOrder());
        for (int i = 0; i < totalMess.size(); i++) {
            for (int j = 0; j < unsortedSellers.size(); j++) {
                int index = unsortedSellers.get(i).indexOf(":");
                if((Integer.parseInt(unsortedSellers.get(i).substring(index + 1))) == totalMess.get(i)) {
                    int ind = unsortedSellers.get(i).indexOf(";");
                    int endInd = unsortedSellers.get(i).indexOf(":");
                    sortedSellers.add(unsortedSellers.get(i).substring(0, ind));
                    sortedStores.add(unsortedSellers.get(i).substring(ind + 2, endInd));
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
            readStores();
            System.out.println("Would you like to sort the sellers by their popularity?\n1.) Yes\n2.) No ");
            int resp = scan.nextInt();
            if (resp == 1) {
                sort();
                for (int i = 0; i < sortedSellers.size(); i++) {
                    System.out.println(sortedSellers.get(i));
                    System.out.println("Their stores: ");
                    System.out.println(sortedStores.get(i) + "\n");
                }
            }else if (resp == 2) {
                for (int i = 0; i < unsortedSellers.size(); i++) {
                    System.out.println(unsortedSellers.get(i));
                    System.out.print("Their stores: ");
                    System.out.println(stores.get(i) + "\n");
                }
            }
            System.out.println("Do you want to send a message to a store?");
            String ans = scan.nextLine();
            if (ans.equals("yes") || ans.equals("Yes")) {
                System.out.println("Which store?");
                store = scan.nextLine();
                System.out.println("Who is the owner of this store?");
                recipient = scan.nextLine();
                Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
            }
        }
        if (response == 2) {
            System.out.println("Which seller are you looking for?\n");
            String seller = scan.nextLine();
            int indexOfSeller;
            boolean found = false;
            while (!found) {
                for (int i = 0; i < sortedSellers.size(); i++) {
                    if (sortedSellers.get(i).equals(seller)) {
                        found = true;
                        indexOfSeller = i;
                    }
                }
                if (found) {
                    System.out.println("The seller was found");
                } else {
                    System.out.println("Seller was not found, try again.");
                }
            }
            System.out.println("Do you want to send a message to this seller?");
            String sendMess = scan.nextLine();
            if (sendMess.equals("yes") || sendMess.equals("Yes")) {
                String store = "";
                Conversation conversation = new Conversation(scan, userName, fileName, seller, store);
            }
        }
        if (response == 3) {
            System.out.println("What censors would you like to add?");
            String censors = scan.nextLine();
        }
        if (response == 4) {

        }
        if (response == 5) {

        }
    }





}
