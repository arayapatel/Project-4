package src;

import java.util.ArrayList;
import java.util.Scanner;

public class Conversation {
    private Scanner scan;
    private String userName;
    private String fileName;
    private String recipient;
    private int option = 0;

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

    public Conversation(Scanner scan, String userName, String fileName, String recipient, String store) {
        this.scan = scan;
        this.userName = userName;
        this.fileName = fileName;
        this.recipient = recipient;
        ConversationReaderWriter crw = new ConversationReaderWriter(userName, recipient,store);
        String[] messageLog = crw.getMessages(); //find exact name
        ArrayList<ArrayList<String[]>> messages = new ArrayList<ArrayList<String[]>>();
        String sender = messageLog[0].split(";",4)[0];
        String time = messageLog[0].split(";",4)[2];
        System.out.printf("%s @%s%n", sender, time);
        for (String message : messageLog) {
            String[] messageSep = message.split(";",4);
            if (messageSep[0].equals(sender) && /* something to check time*/) {
                System.out.println(messageSep[3]);

            }

        }
            //System.out.printf("%s"); //usern, read, time, message
        //}
        do {
            System.out.println("What do you want to do?/n");
        } while (option != 5);
    }

    public void sendMessage() {
        scan.


    }



}
