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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public Conversation(Scanner scan, String userName, String fileName, String recipient) {
        this.scan = scan;
        this.userName = userName;
        this.fileName = fileName;
        this.recipient = recipient;
        ConversationReaderWriter crw = new ConversationReaderWriter()
        do {
            System.out.println("What do you want to do?");
        } while (option != 5);
    }

    public void sendMessage() {
        scan.


    }



}
