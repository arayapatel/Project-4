package src;

import java.util.ArrayList;
import java.util.Scanner;

public class Conversation {
    private Scanner scan;
    private String userName;
    private String fileName;
    private String recipient;
    private int option = 0;

    public Conversation(Scanner scan, String userName, String fileName, String recipient, String store) {
        this.scan = scan;
        this.userName = userName;
        this.fileName = fileName;
        this.recipient = recipient;
        ConversationReaderWriter crw = new ConversationReaderWriter(userName, recipient, store);
        String[] messageLog = crw.getMessages(); //find exact name
        ArrayList<ArrayList<String[]>> messages = new ArrayList<ArrayList<String[]>>();
        String sender = messageLog[0].split(";", 4)[0];
        String timeString = messageLog[0].split(";", 4)[2];
        int time = Integer.parseInt(timeString.substring(9, 11)) * 60 + Integer.parseInt(timeString.substring(12, 14));
        System.out.printf("%s @%d%n", sender, time);
        for (String message : messageLog) {
            String[] messageSep = message.split(";", 4);
            String tempTimeString = messageLog[0].split(";", 4)[2];
            int tempTime = Integer.parseInt(tempTimeString.substring(9, 11)) * 60
                    + Integer.parseInt(tempTimeString.substring(12, 14));
            if (!(messageSep[0].equals(sender) && (Math.abs(tempTime - time) < 30))) {
                sender = messageSep[0];
                timeString = messageSep[2];
                time = Integer.parseInt(timeString.substring(9, 11)) * 60 + Integer.parseInt(timeString.substring(12, 14));
                System.out.printf("%s @%d%n", sender, time);
            }
            System.out.println(messageSep[3]);

        }
        //System.out.printf("%s"); //usern, read, time, message
        // xx/xx/xx xx:xx
        //}
        do {
            System.out.println("What do you want to do?/n");
        } while (option != 5);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getOption() {
        return option;
    }
//    public void sendMessage() {
//        scan.
//
//
//    }
//


}
