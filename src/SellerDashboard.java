package src;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
import java.util.Scanner;



public class SellerDashboard {
    public String fileName = "";
    public String userName = "";
    public Scanner scan = new Scanner(System.in);
    public String recipient = "";
    public int response = 0;
    public boolean again = true;

    public SellerDashboard(Scanner scan, String userName, String fileName ) {
        this.fileName = fileName;
        this.userName = userName;
        this.scan = scan;
        startMessage();
        while (again) {
            forward();
        }


    }
    // Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
    ArrayList<String> unsortedBuyers = new ArrayList<String>();
    ArrayList<String> fileContents = new ArrayList<String>();
    public void readBuyers() {
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
        for (int i = 0; i < fileContents.size(); i++) {
            int index = fileContents.get(i).indexOf(";");
            unsortedBuyers.add((fileContents.get(i).substring(0, index)));
        }

    }

    ArrayList<Integer> totalMess = new ArrayList<Integer>();
    public void totalMessages() {
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

        for (int i = 0; i < fileContents.size(); i++) {
            int index = fileContents.get(i).indexOf(";");
            totalMess.add(Integer.parseInt(fileContents.get(i).substring(index + 1)));
        }

    }
    ArrayList<String> sortedBuyers = new ArrayList<String>();
    public void sort() {
        totalMessages();
        readBuyers();
        Collections.sort(totalMess, Collections.reverseOrder());
        for (int i = 0; i < totalMess.size(); i++) {
            for (int j = 0; j < unsortedBuyers.size(); j++) {
                int index = unsortedBuyers.get(i).indexOf(";");
                if((Integer.parseInt(unsortedBuyers.get(j).substring(index + 1))) == totalMess.get(i)) {
                    int ind = unsortedBuyers.get(j).indexOf(";");
                    sortedBuyers.add(unsortedBuyers.get(j).substring(0, ind));
                }
            }
        }
    }
    public void startMessage() {
        String print = String.format("Welcome to the Dashboard!\nChoose what you would like to do\n\n" +
                "1.) View/Send to buyers\n" +
                "2.) Search for a buyer\n" +
                "3.) Add a new censor\n" +
                "4.) Export\n" +
                "5.) Exit\n");
        System.out.println(print);
        response = scan.nextInt();
    }
    public void forward() {
        //startMessage();
        if (response == 1) {
            ArrayList<String> allStores = new ArrayList<String>();
            readBuyers();
            System.out.println("Would you like to sort the buyers by their popularity?\n1.) Yes\n2.) No ");
            int resp = scan.nextInt();
            if (resp == 1) {
                sort();
                for (int i = 0; i < sortedBuyers.size(); i++) {
                    System.out.println(sortedBuyers.get(i));
                }
            }else if (resp == 2) {
                for (int i = 0; i < unsortedBuyers.size(); i++) {
                    System.out.println(unsortedBuyers.get(i));
                }
            }
            System.out.println("Do you want to send a message to a buyer?");
            String ans = scan.nextLine();
            if (ans.equals("yes") || ans.equals("Yes")) {
                System.out.println("Which buyer?");
                recipient = scan.nextLine();
                String store = "General";
                Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
            }
        }
        if (response == 2) {
            int indexOfBuyer;
            boolean found = false;
            String searchForBuyer = "";
            while (!found) {
                System.out.println("Which buyer are you looking for?\n");
                searchForBuyer = scan.nextLine();
                for (int i = 0; i < sortedBuyers.size(); i++) {
                    if (sortedBuyers.get(i).equals(searchForBuyer)) {
                        found = true;
                        indexOfBuyer = i;
                    }
                }
                if (found) {
                    System.out.println("The buyer was found");
                } else {
                    System.out.println("Buyer was not found, try again.");
                }
            }
            System.out.println("Do you want to send a message to this buyer?");
            String sendMess = scan.nextLine();
            if (sendMess.equals("yes") || sendMess.equals("Yes")) {
                String store = "";
                Conversation conversation = new Conversation(scan, userName, fileName, searchForBuyer, store);
            }
        }
        if (response == 3) {
            System.out.println("What censors would you like to add?");
            String censors = scan.nextLine();
        }
        if (response == 4) {

        }
        if (response == 5) {
            again = true;
        }

    }





}
