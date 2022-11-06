package src;

import java.io.*;
import java.util.ArrayList;

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
                //System.out.println(line);
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
                    return messages;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean writeMessage() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(myUser)));
            PrintWriter pw = new PrintWriter(new FileWriter(new File(myUser)));
            ArrayList<String> tempFile = new ArrayList<String>();
            tempFile.add(br.readLine());
            while (!tempFile.get(tempFile.size()).equals("-----")) {
                tempFile.add(br.readLine());
            }
            tempFile.add(br.readLine());
            ArrayList<String> messageLines = new ArrayList<>();
            while (tempFile.get(tempFile.size()) != null) {
                tempFile.add(br.readLine());
            }

            for (int i = 0; i < messageLines.size(); i++) {
                String[] oneLine = messageLines.get(i).split(", ");
                if (oneLine[1].equals(recievingUser) && oneLine[2].equals(store)) {
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        ConversationReaderWriter c1 = new ConversationReaderWriter("SampleSeller", "buyerUsername", "store1");
        String[] recievedMessages = c1.getMessages();

        for (int i = 0; i < recievedMessages.length; i++) {
            System.out.println(recievedMessages[i]);
        }
    }
}
