package src;

import java.io.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ConversationReaderWriter {
    private String myUser;
    public String recievingUser;
    private String store;
    private String[] messages;


    public ConversationReaderWriter(String myUser, String recievingUser, String store) {
        this.myUser = myUser;
        this.recievingUser = recievingUser;
        this.store = store;
        this.messages = readMessages();
    }

    public String[] readMessages() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(myUser)));
            String line = br.readLine();
            while (!line.equals("-----")) {
                line = br.readLine();
            }
            line = br.readLine();
            ArrayList<String> messageLines = new ArrayList<>();
            while (line != null) {
                messageLines.add(line);
                line = br.readLine();
            }

            for (int i = 0; i < messageLines.size(); i++) {
                String[] oneLine = messageLines.get(i).split(", ");
                if (oneLine[1].equals(recievingUser) && oneLine[2].equals(store)) {
                    String[] messages = oneLine[3].split("],\\[");
                    if (messages != null) {
                        messages[0] = messages[0].substring(1);
                        messages[messages.length - 1] = messages[messages.length - 1].substring(0, messages[messages.length - 1].length() - 1);
                    }
                    br.close();
                    return messages;
                }
            }
            br.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean writeMessage(String message) {
        try {
            File f = null;
            BufferedReader br = null;
            PrintWriter pw = null;
            PrintWriter clearer = null;
            String isRead;
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    f = new File(myUser);
                    isRead = "true";
                } else {
                    f = new File(recievingUser);
                    isRead = "false";
                }
                br = new BufferedReader(new FileReader(f));
                pw = new PrintWriter(new FileOutputStream(f, true));
                ArrayList<String> tempFile = new ArrayList<String>();
                tempFile.add(br.readLine());

                //---
                System.out.println("\nThis: " + tempFile.get(tempFile.size() - 1));
                //---

                while (!tempFile.get(tempFile.size() - 1).equals("-----")) {
                    tempFile.add(br.readLine());
                }
                tempFile.add(br.readLine());
                boolean found = false;
                while (tempFile.get(tempFile.size() - 1) != null) {
                    String[] oneLine = tempFile.get(tempFile.size() - 1).split(", ");
                    if (oneLine[1].equals(recievingUser) && oneLine[2].equals(store)) {
                        found = true;
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String timestamp = dtf.format(now);
                        tempFile.set(tempFile.size() - 1, tempFile.get(tempFile.size() - 1) + String.format(",[%s;%s;%s;%s]", myUser, isRead, timestamp, message));
                    }
                    tempFile.add(br.readLine());
                }
                if (!found) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String timestamp = dtf.format(now);
                    if (isRead.equals("true")) {
                        tempFile.add(String.format("1, %s, %s, [%s;%s;%s;%s]", myUser, store, myUser, isRead, timestamp, message));
                    } else {
                        tempFile.add(String.format("0, %s, %s, [%s;%s;%s;%s]", myUser, store, myUser, isRead, timestamp, message));
                    }
                }
                clearer = new PrintWriter(new FileOutputStream(f, false));
                for (int j = 0; j < tempFile.size(); j++) {
                    pw.println(tempFile.get(j));
                }
            }
            br.close();
            pw.close();
            clearer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean invisibleMessage(String message) {
        return true;
    }

    public boolean editMessage(String message) {
        return true;
    }

    public boolean deleteMessage(String message) {
        return true;
    }

    public String[] getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        ConversationReaderWriter c1 = new ConversationReaderWriter("SampleBuyer", "SampleSeller", "general");
        String[] recievedMessages = c1.getMessages();

        for (int i = 0; i < recievedMessages.length; i++) {
            System.out.println(recievedMessages[i]);
        }

        System.out.println(c1.writeMessage("Attempt1"));
    }
}
