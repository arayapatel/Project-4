package src;

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

    public Conversation() {
        this.scan = scan;
        this.userName = userName;
        this.fileName = fileName;
        this.recipient = recipient;
        ConversationReaderWriter crw = new ConversationReaderWriter(userName, recipient,store);
        String[] messageLog = crw.getMessages(); //find exact name
        //message.split(";",4)
        for (String message : messageLog) {
            System.out.printf("%s"); //usern, read, time, message
        }
        do {
            System.out.println("What do you want to do?/n");
        } while (option != 5);
    }

    public void sendMessage() {
        scan.


    }



}
