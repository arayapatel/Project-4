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
            BufferedReader br = new BufferedReader(new FileReader(myUser));
            ArrayList<String> storedLines = new ArrayList<String>();
            String line = br.readLine();
            storedLines.add(line);
            while (!line.equals("-----")) {
                line = br.readLine();
                storedLines.add(line);
            }
            line = br.readLine();
            storedLines.add(line);
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
                        if (this.checkInvisibleMessage(i)) {
                            for (int j = 0; j < messages.length; j++) {
                                String[] linePieces = messages[j].split(";");
                                if (linePieces[1].charAt(0) == '/') {
                                    linePieces[1] = linePieces[1].substring(1);
                                    line = "";
                                    for (int k = 0; k < linePieces.length; k++) {
                                        if (line.length() > 0) {
                                            line += ";";
                                        }
                                        line += linePieces[k];
                                    }
                                    messages[j] = line;
                                }
                            }
                        }
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

    private boolean checkInvisibleMessage(int lineLocation) {
        try {
            File f = new File(myUser);
            BufferedReader br;
            PrintWriter pw;
            PrintWriter clearer;
            br = new BufferedReader(new FileReader(f));
            ArrayList<String> tempFile = new ArrayList<String>();

            tempFile.add(br.readLine());
            while (!tempFile.get(tempFile.size() - 1).equals("-----")) {
                tempFile.add(br.readLine());
            }

            for (int i = 0; i < lineLocation + 1; i++) {
                tempFile.add(br.readLine());
            }

            boolean didChange = false;
            System.out.println("lastelement " + tempFile.get(tempFile.size() - 1));
            String[] messages = tempFile.get(tempFile.size() - 1).split(", ")[3].split("],\\[");
            String changingLine = "";
            for (int i = 0; i < messages.length; i++) {
                String[] splitMessage = messages[i].split(";");
                if (!(splitMessage[1].charAt(0) == '/')) {
                    if (changingLine.length() == 0) {
                        changingLine += tempFile.get(tempFile.size() - 1).split(", ")[0] + ", " + tempFile.get(tempFile.size() - 1).split(", ")[1] + ", " + tempFile.get(tempFile.size() - 1).split(", ")[2] + ", ";
                    }
                    String tempLine = tempFile.get(tempFile.size() - 1).split(", ")[3];
                    changingLine += tempLine.substring(0,tempLine.length() - 1).split("],\\[")[i] + "],[";
                    didChange = true;
                }
            }
            tempFile.set(tempFile.size() - 1, changingLine.substring(0,changingLine.length() - 3) + "]");

            while (tempFile.get(tempFile.size() - 1) != null) {
                tempFile.add(br.readLine());
            }
            if (tempFile.get(tempFile.size() - 1) == null) {
                tempFile.remove(tempFile.size() - 1);
            }

            br.close();
            clearer = new PrintWriter(new FileOutputStream(f, false));
            clearer.print("");
            clearer.close();

            pw = new PrintWriter(new FileOutputStream(f, true));
            for (int j = 0; j < tempFile.size(); j++) {
                pw.println(tempFile.get(j));
            }
            pw.close();
            return didChange;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeMessage(String message) {
        try {
            File f;
            BufferedReader br;
            PrintWriter pw;
            PrintWriter clearer;
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
                ArrayList<String> tempFile = new ArrayList<String>();
                tempFile.add(br.readLine());

                while (!tempFile.get(tempFile.size() - 1).equals("-----")) {
                    tempFile.add(br.readLine());
                }

                tempFile.add(br.readLine());
                boolean found = false;
                while (tempFile.get(tempFile.size() - 1) != null) {
                    String[] oneLine = tempFile.get(tempFile.size() - 1).split(", ");
                    boolean correctPerson;
                    if (isRead.equals("true")) {
                        correctPerson = oneLine[1].equals(recievingUser);
                    } else {
                        correctPerson = oneLine[1].equals(myUser);
                    }
                    if (correctPerson && oneLine[2].equals(store)) {
                        found = true;
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String timestamp = dtf.format(now);
                        String lineToChange = tempFile.get(tempFile.size() - 1);
                        if (isRead.equals("false")) {
                            lineToChange = (Integer.parseInt(lineToChange.substring(0, 1)) + 1) + lineToChange.substring(1);
                        }
                        tempFile.set(tempFile.size() - 1, lineToChange + String.format(",[%s;%s;%s;%s]", myUser, isRead, timestamp, message));
                    }
                    tempFile.add(br.readLine());
                }
                if (tempFile.get(tempFile.size() - 1) == null) {
                    tempFile.remove(tempFile.size() - 1);
                }
                br.close();
                if (!found) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String timestamp = dtf.format(now);
                    if (isRead.equals("true")) {
                        tempFile.add(String.format("0, %s, %s, [%s;%s;%s;%s]", myUser, store, myUser, isRead, timestamp, message));
                    } else {
                        tempFile.add(String.format("1, %s, %s, [%s;%s;%s;%s]", myUser, store, myUser, isRead, timestamp, message));
                    }
                }
                clearer = new PrintWriter(new FileOutputStream(f, false));
                clearer.print("");
                clearer.close();

                pw = new PrintWriter(new FileOutputStream(f, true));
                for (int j = 0; j < tempFile.size(); j++) {
                    pw.println(tempFile.get(j));
                }
                pw.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean invisibleMessage(String message) {
        try {
            File f;
            BufferedReader br;
            PrintWriter pw;
            PrintWriter clearer;
            String isRead;
            f = new File(recievingUser);
            isRead = "false";
            br = new BufferedReader(new FileReader(f));
            ArrayList<String> tempFile = new ArrayList<String>();
            tempFile.add(br.readLine());

            while (!tempFile.get(tempFile.size() - 1).equals("-----")) {
                tempFile.add(br.readLine());
            }

            tempFile.add(br.readLine());
            boolean found = false;
            while (tempFile.get(tempFile.size() - 1) != null) {
                String[] oneLine = tempFile.get(tempFile.size() - 1).split(", ");
                boolean correctPerson;
                if (isRead.equals("true")) {
                    correctPerson = oneLine[1].equals(recievingUser);
                } else {
                    correctPerson = oneLine[1].equals(myUser);
                }
                if (correctPerson && oneLine[2].equals(store)) {
                    found = true;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String timestamp = dtf.format(now);
                    String lineToChange = tempFile.get(tempFile.size() - 1);
                    lineToChange = (Integer.parseInt(lineToChange.substring(0, 1)) + 1) + lineToChange.substring(1);
                    tempFile.set(tempFile.size() - 1, lineToChange + String.format(",[%s;/%s;%s;%s]", myUser, isRead, timestamp, message));
                }
                tempFile.add(br.readLine());
            }
            if (tempFile.get(tempFile.size() - 1) == null) {
                tempFile.remove(tempFile.size() - 1);
            }
            br.close();
            if (!found) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String timestamp = dtf.format(now);
                tempFile.add(String.format("1, %s, %s, [%s;/%s;%s;%s]", myUser, store, myUser, isRead, timestamp, message));
            }
            clearer = new PrintWriter(new FileOutputStream(f, false));
            clearer.print("");
            clearer.close();

            pw = new PrintWriter(new FileOutputStream(f, true));
            for (int j = 0; j < tempFile.size(); j++) {
                pw.println(tempFile.get(j));
            }
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editMessage(int one, String message) {

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
        /*String[] recievedMessages = c1.getMessages();
        if (recievedMessages != null) {
            for (int i = 0; i < recievedMessages.length; i++) {
                System.out.println(recievedMessages[i]);
            }
        } else {
            System.out.println("null");
        }*/

        System.out.println(c1.invisibleMessage("SNEEKY"));
        ConversationReaderWriter c2 = new ConversationReaderWriter("SampleSeller", "SampleBuyer", "general");
        String[] recievedMessages = c2.getMessages();
        if (recievedMessages != null) {
            for (int i = 0; i < recievedMessages.length; i++) {
                System.out.println(recievedMessages[i]);
            }
        } else {
            System.out.println("null");
        }
    }
}
