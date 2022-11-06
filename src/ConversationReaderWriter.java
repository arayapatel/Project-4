import java.io.*;
import java.util.ArrayList;

public class ConversationReaderWriter {
    private String myUser;
    public String recievingUser;
    private String recievingStore;
    private ArrayList<String> messages;


    public ConversationReaderWriter(String myUser, String recievingUser, String recievingStore) {
        this.myUser = myUser;
        this.recievingUser = recievingUser;
        this.recievingStore = recievingStore;
        messages = readMessages();
    }

    public ArrayList<String> readMessages() {
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
            return messageLines;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed");
            return null;
        }
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        ConversationReaderWriter c1 = new ConversationReaderWriter("SampleSeller", "buyerUsername", "store1");
        ArrayList<String> messages = c1.getMessages();
        /*for (int i = 0; i < messages.size(); i++) {
            System.out.println(messages.get(i));
        }*/
    }
}
