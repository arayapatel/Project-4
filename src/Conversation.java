package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Conversation {
    private Scanner scan;
    private String userName;
    private String fileName;
    private String recipient;
    private int option = 0;
    private ConversationReaderWriter crw;

    public Conversation(Scanner scan, String userName, String fileName, String recipient, String store) {
        this.scan = scan;
        this.userName = userName;
        this.fileName = fileName;
        this.recipient = recipient;
        crw = new ConversationReaderWriter(userName, recipient, store);
        String[] messageLog = crw.readMessages(); //find exact name
        if (messageLog != null) {
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
        }
        do {
            System.out.printf("What do you want to do?%n1. Send Message%n2. Send Invisible Message%n3. Import " +
                    "File%n4. Edit Previous Message%n5. Delete Message%n6. Exit Chat%n");
            String choice = scan.nextLine().trim();
            int option;
            try {
                option = Integer.parseInt(choice);
            } catch (Exception e) {
                option = 10;
            }
            switch (option) {
                case 1:
                    sendMessage();
                    break;
                case 2:
                    sendInvisibleMessage();
                    break;
                case 3:
                    importFile();
                    break;
                case 4:
                    editMessage();
                    break;
                case 5:
                    deleteMessage();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        } while (option != 6);
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

    public void sendMessage() {
        System.out.println("What would you like to send?");
        String msg = scan.nextLine();
        if (msg == null) {
            System.out.println("Error: no message received");
        } else{
            crw.writeMessage(msg);
        }
    }

    public void sendInvisibleMessage() {
        System.out.println("Now in Invisible Mode...");
        System.out.println("What would you like to send?");
        String msg = scan.nextLine();
        if (msg == null) {
            System.out.println("Error: no message received");
        } else{
            crw.invisibleMessage(msg);
        }
    }

    public void importFile() {
        System.out.println("Type the file path");
        String fileName = scan.nextLine();
        String msg = "";
        try {
            File file = new File(fileName);
            if (!(file.exists()) || file.isDirectory()){
                throw new FileNotFoundException();
            }
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                msg += line + "/n";
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            System.out.println("Invalid File");
        }
        if (msg == null) {
            System.out.println("Error: no message received");
        } else{
            crw.writeMessage(msg);
        }
    }

    public void editMessage() {
        System.out.println("Preparing Messages to edit...\nSelect the number of the message you want to edit.");

        String msg = "";

        String[] messageLog = crw.readMessages(); //find exact name
        if (messageLog != null) {
            ArrayList<ArrayList<String[]>> messages = new ArrayList<ArrayList<String[]>>();
            String sender = messageLog[0].split(";", 4)[0];
            String timeString = messageLog[0].split(";", 4)[2];
            int time = Integer.parseInt(timeString.substring(9, 11)) * 60 + Integer.parseInt(timeString.substring(12, 14));
            System.out.printf("%s @%d%n", sender, time);
            int count = 1;
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
                System.out.printf("%s: %s", count,messageSep[3]);

            }


            System.out.println("What would you like to edit?");
            count = scan.nextInt();
            count -=1;
            scan.nextLine();
            System.out.println("What would you like to replace it with?");
            msg = scan.nextLine();
            if (msg == null) {
                System.out.println("Error: no message received");
            } else {
                crw.editMessage(count, msg);
            }

        }
    }

    public void deleteMessage() {
        System.out.println("Preparing Messages to Delete...\nSelect the number of the message you want to delete.");

        String msg = "";

        String[] messageLog = crw.readMessages(); //find exact name
        if (messageLog != null) {
            ArrayList<ArrayList<String[]>> messages = new ArrayList<ArrayList<String[]>>();
            String sender = messageLog[0].split(";", 4)[0];
            String timeString = messageLog[0].split(";", 4)[2];
            int time = Integer.parseInt(timeString.substring(9, 11)) * 60 + Integer.parseInt(timeString.substring(12, 14));
            System.out.printf("%s @%d%n", sender, time);
            int count = 1;
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
                System.out.printf("%s: %s", count, messageSep[3]);

            }


            System.out.println("What would you like to delete?");
            count = scan.nextInt();
            count -= 1;
            scan.nextLine();
            crw.deleteMessage(count);
        }
    }
}
