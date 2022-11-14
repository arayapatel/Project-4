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

    public ArrayList<String> readStores() {
        ArrayList<String> fileContents = new ArrayList<String>();
        ArrayList<String> stores = new ArrayList<String>();
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
                stores.add(fileContents.get(i).substring(startIndex + 2, endIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       // return fileContents, stores;
        return stores;
    }

    public ArrayList<Integer> totalMessages() {
        ArrayList<Integer> totalMess = new ArrayList<Integer>();
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("src/Seller.txt"));
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
            int index = fileContents.get(i).indexOf(":");
            totalMess.add(Integer.parseInt(fileContents.get(i).substring(index + 2)));

        }
        return totalMess;
    }


    ArrayList<String> unsortedSellers = new ArrayList<String>();
    ArrayList<String> sortedStores = new ArrayList<String>();
    public ArrayList<String> sortSellers() {
        ArrayList<Integer> totalMess = totalMessages();
        ArrayList<String> sortedSellers = new ArrayList<String>();
        Collections.sort(totalMess, Collections.reverseOrder());
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("src/Seller.txt"));
            String line = bfr.readLine();
            while (line != null) {
                fileContents.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < totalMess.size(); i++) {
            for (int j = 0; j < fileContents.size(); j++) {
                int index = fileContents.get(i).indexOf(":");
                int ind = fileContents.get(i).indexOf(";");
                if((Integer.parseInt(fileContents.get(i).substring(index + 2))) == totalMess.get(i)) {
                    sortedSellers.add(fileContents.get(i).substring(0, ind));
                    //sortedStores.add(fileContents.get(i).substring(ind + 2, index));
                }
                unsortedSellers.add(fileContents.get(i).substring(0, ind));
            }
        }
        return sortedSellers;
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
    public String[] messagesSentToStore() {
        ArrayList<String> stores = new ArrayList<String>();
        ArrayList<Integer> numMessages = new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(userName));
            ArrayList<String> storedLines = new ArrayList<String>();
            String line = br.readLine();
            storedLines.add(line);
            while (!line.equals("-----")) {
                line = br.readLine();
                storedLines.add(line);
            }
            line = br.readLine();
            storedLines.add(line);
            ArrayList<String> messageLines = new ArrayList<>();
            while (line != null) {
                messageLines.add(line);
                line = br.readLine();
            }

            for (int i = 0; i < messageLines.size(); i++) {
                String[] oneLine = messageLines.get(i).split(", ");
                if (!oneLine[2].equals("general")) {
                    ConversationReaderWriter crw = new ConversationReaderWriter(userName, oneLine[1], oneLine[2]);
                    stores.add(oneLine[2]);
                    String[] messagesOfStores = crw.readMessages();
                    int messagesOfStore = 0;
                    for (int j = 0; j < messagesOfStores.length; j++) {
                        if (messagesOfStores[j].split(";", 3)[0].equals(userName)) {
                            messagesOfStore++;
                        }
                    }
                    numMessages.add(messagesOfStore);
                }
            }

            if (stores == null || stores.size() == 0) {
                return null;
            }
            String[] storesInOrder = new String[stores.size()];
            int placeInArray = 0;
            while (stores != null && stores.size() > 0) {
                int highestStoreMessages = 0;
                int highestNumMessages = 0;
                for (int i = 0; i < stores.size(); i++) {
                    if (numMessages.get(i) >= highestNumMessages) {
                        highestStoreMessages = i;
                        highestNumMessages = numMessages.get(i);
                    }
                }
                storesInOrder[placeInArray] = stores.get(highestStoreMessages);
                stores.remove(highestStoreMessages);
                numMessages.remove(highestStoreMessages);
                placeInArray++;
            }

            return storesInOrder;
        } catch (Exception e) {
            return null;
        }
    }

    public void startMessage() {
        String print = String.format("Welcome to the Buyer Dashboard!\nChoose what you would like to do\n" +
                                     "1) View/Send to stores\n" +
                                     "2) Search for a seller\n" +
                                     "3) Add a new censor\n" +
                                     "4) Export\n" +
                                     "5) Exit\n");
        System.out.println(print);
        response = scan.nextInt();
    }


    public void forward() {
        //startMessage();
        //totalMessages();
        ArrayList<String> sortedSellers = sortSellers();
        String[] storesInOrder = messagesSentToStore();
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
                    //System.out.println(stores.get(i) + "\n");
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
                ArrayList<String> fileContents = new ArrayList<String>();
                seller = scan.next();
                try {
                    BufferedReader bfr = new BufferedReader(new FileReader("src/Seller.txt"));
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
                    if (fileContents.get(i).contains(seller)) {
                        found = true;
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
