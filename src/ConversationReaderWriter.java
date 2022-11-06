import java.io.*;
import java.util.ArrayList;

public class ConversationReaderWriter {
    private String myUser;
    public String recievingUser;
    private String recievingStore;
    private String[] messages;


    public ConversationReaderWriter(String myUser, String recievingUser, String recievingStore) {
        this.myUser = myUser;
        this.recievingUser = recievingUser;
        this.recievingStore = recievingStore;
        messages = readMessages();
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
                System.out.println(line);
                line = br.readLine();
            }

            for (int i = 0; i < messageLines.size(); i++) {
                String[] oneLine = messageLines.get(i).split(", ");
                if (oneLine[0].equals(recievingUser) && oneLine[1].equals(recievingStore)) {
                    return oneLine;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        ConversationReaderWriter c1 = new ConversationReaderWriter("SampleSeller", "buyerUsername", "store1");
        String[] messages = c1.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println(messages[i]);
        }
    }
}
