package src;

import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
import java.io.FileReader;


public class BuyerDashboard {
    public String fileName = "";
    public String userName = "";
    public Scanner scan = new Scanner(System.in);
    public String recipient = "";
    public String store = "";
    public int response = 0;
    public boolean again = true;

    public BuyerDashboard(Scanner scan, String userName, String filename) {
        this.fileName = filename;
        this.userName = userName;
        this.scan = scan;
        /*
        print stuff
        get input
         */
        startMessage();
        while (again) {
            forward();
        }




    }
   // Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
    ArrayList<String> stores = new ArrayList<String>();
    /*public void readStores() {

        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("src/Seller.txt"));
            String line = bfr.readLine();
            while (line != null) {
                fileContents.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            for (int i = 0; i < fileContents.size(); i++) {
                int startIndex = fileContents.get(i).indexOf(";");
                int endIndex = fileContents.get(i).indexOf(":");
                stores.add(unsortedSellers.get(i).substring(startIndex + 1, endIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return null;
    }*/

    ArrayList<String> fileContents = new ArrayList<String>();
    ArrayList<Integer> totalMess = new ArrayList<Integer>();
    public void totalMessages() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("src/Seller.txt"));
            String line = bfr.readLine();
            while (line != null) {
                fileContents.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            for (int i = 0; i < fileContents.size(); i++) {
                int index = fileContents.get(i).indexOf(":");
                totalMess.add(Integer.parseInt(fileContents.get(i).substring(index + 2)));
                int startIndex = fileContents.get(i).indexOf(";");
                int endIndex = fileContents.get(i).indexOf(":");
                stores.add(unsortedSellers.get(i).substring(startIndex + 1, endIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ArrayList<String> sortedSellers = new ArrayList<String>();
    ArrayList<String> unsortedSellers = new ArrayList<String>();
    ArrayList<String> sortedStores = new ArrayList<String>();
    public void sort() {
        totalMessages();
        Collections.sort(totalMess, Collections.reverseOrder());
        for (int i = 0; i < totalMess.size(); i++) {
            for (int j = 0; j < fileContents.size(); j++) {
                int index = fileContents.get(i).indexOf(":");
                if((Integer.parseInt(fileContents.get(i).substring(index + 2))) == totalMess.get(i)) {
                    int ind = fileContents.get(i).indexOf(";");
                    int endInd = fileContents.get(i).indexOf(":");
                    sortedSellers.add(fileContents.get(i).substring(0, ind));
                    sortedStores.add(fileContents.get(i).substring(ind + 2, endInd));
                }
                int ind = fileContents.get(i).indexOf(";");
                int endInd = fileContents.get(i).indexOf(":");
                unsortedSellers.add(fileContents.get(i).substring(0, ind));
            }
        }
    }
    public void exportFile() {
        ArrayList<String> fileContent = new ArrayList<String>();
        try {
            File file = new File("csv.csv");
            FileWriter outputFile = new FileWriter(file);
            BufferedReader bfr = new BufferedReader(new FileReader(new File(userName)));
            String line = bfr.readLine();
            while (line != null) {
                fileContent.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            int index = 0;
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).contains("-----")) {
                    index = i;
                }
            }

           //CSVWriter writer = new CSVWriter(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void startMessage() {
        String print = String.format("Welcome to the Buyer Dashboard!\nChoose what you would like to do\n\n" +
                                     "1.) View/Send to stores\n" +
                                     "2.) Search for a seller\n" +
                                     "3.) Add a new censor\n" +
                                     "4.) Export\n" +
                                     "5.) Exit\n");
        System.out.println(print);
        response = scan.nextInt();
    }


    public void forward() {
        //startMessage();
        //totalMessages();
        sort();
        if (response == 1) {
            ArrayList<String> allStores = new ArrayList<String>();
           //readStores();
            System.out.println("Would you like to sort the sellers by their popularity?\n1.) Yes\n2.) No ");
            int resp = scan.nextInt();
            if (resp == 1) {
                //sort();
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
            String ans = scan.next();
            if (ans.equals("yes") || ans.equals("Yes")) {
                System.out.println("Which store?");
                store = scan.next();
                System.out.println("Who is the owner of this store?");
                recipient = scan.next();
                Conversation conversation = new Conversation(scan, userName, fileName, recipient, store);
            }
        }
        if (response == 2) {
            int indexOfSeller;
            boolean found = false;
            String seller = "";
            while (!found) {
                System.out.println("Which seller are you looking for?\n");
                seller = scan.next();
                for (int i = 0; i < sortedSellers.size(); i++) {
                    if (sortedSellers.get(i).equals(seller)) {
                        found = true;
                        indexOfSeller = i;
                    }
                }
                if (found == true) {
                    System.out.println("The seller was found");
                } else {
                    System.out.println("Seller was not found, try again.");
                }
            }
            System.out.println("Do you want to send a message to this seller?");
            String sendMess = scan.next();
            if (sendMess.equals("yes") || sendMess.equals("Yes")) {
                String store = "";
                Conversation conversation = new Conversation(scan, userName, fileName, seller, store);
            }
        }
        if (response == 3) {
            System.out.println("What censors would you like to add? (Separated by comma)");
            String censors = scan.next();
        }
        if (response == 4) {
            //./csv();
        }

        if (response == 5) {
            again = false;
        }

    }






}
