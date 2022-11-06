package src;

import java.io.*;
import java.util.ArrayList;

public class ConversationReaderWriter {
    private String myUser;
    public String recievingUser;
    private String recievingStore;
    private String[] recievedMessages;
    private String[] sentMessages;


    public ConversationReaderWriter(String myUser, String recievingUser, String recievingStore) {
        this.myUser = myUser;
        this.recievingUser = recievingUser;
        this.recievingStore = recievingStore;
        String[] messages = readMessages();
        recievedMessages = messages[0].split("],\\[");
        if (recievedMessages != null) {
            recievedMessages[0] = recievedMessages[0].substring(1);
            recievedMessages[recievedMessages.length - 1] = recievedMessages[recievedMessages.length - 1].substring(0, recievedMessages[recievedMessages.length - 1].length() - 1);
        }
        sentMessages = messages[1].split("],\\[");
        if (sentMessages != null) {
            sentMessages[0] = sentMessages[0].substring(1);
            sentMessages[sentMessages.length - 1] = sentMessages[sentMessages.length - 1].substring(0, sentMessages[sentMessages.length - 1].length() - 1);
        }
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
                if (oneLine[1].equals(recievingUser) && oneLine[2].equals(recievingStore)) {
                    return oneLine[3].split("\\|");
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
                if (oneLine[1].equals(recievingUser) && oneLine[2].equals(recievingStore)) {
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getRecievedMessages() {
        return recievedMessages;
    }

    public String[] getSentMessages() {
        return sentMessages;
    }

    public static void main(String[] args) {
        ConversationReaderWriter c1 = new ConversationReaderWriter("SampleSeller", "buyerUsername", "store1");
        String[] recievedMessages = c1.getRecievedMessages();
        String[] sentMessages = c1.getSentMessages();

        for (int i = 0; i < recievedMessages.length; i++) {
            System.out.println(recievedMessages[i]);
        }

        for (int i = 0; i < sentMessages.length; i++) {
            System.out.println(sentMessages[i]);
        }
    }
}
