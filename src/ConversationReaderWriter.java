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
                                String[] linePieces = messages[j].split(";", 4);
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
                    String[] tempFinalMessages = new String[messages.length];
                    int placement = 0;
                    for (int j = 0; j < messages.length; j++) {
                        if (!messages[j].split(";", 4)[1].equals("deleted")) {
                            tempFinalMessages[placement] = messages[j];
                            placement++;
                        }
                    }
                    String[] finalMessages = new String[placement];
                    for (int j = 0; j < finalMessages.length; j++) {
                        finalMessages[j] = tempFinalMessages[j];
                    }
                    return finalMessages;
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
            String[] messages = tempFile.get(tempFile.size() - 1).split(", ")[3].split("],\\[");
            String changingLine = "";
            for (int i = 0; i < messages.length; i++) {
                String[] splitMessage = messages[i].split(";", 4);
                if (!(splitMessage[1].charAt(0) == '/')) {
                    if (changingLine.length() == 0) {
                        changingLine += "0, " + tempFile.get(tempFile.size() - 1).split(", ")[1] + ", " + tempFile.get(tempFile.size() - 1).split(", ")[2] + ", ";
                    }
                    String tempLine = tempFile.get(tempFile.size() - 1).split(", ")[3];
                    String[] splitTempLine = tempLine.substring(0, tempLine.length() - 1).split("],\\[");
                    for (int j = 0; j < splitTempLine.length; j++) {
                        String[] individualMessages = splitTempLine[j].split(";", 4);
                        if (individualMessages[1].equals("false")) {
                            individualMessages[1] = "true";
                            splitTempLine[j] = "";
                            for (int k = 0; k < individualMessages.length; k++) {
                                if (k > 0) {
                                    splitTempLine[j] += ";";
                                }
                                splitTempLine[j] += individualMessages[k];
                            }
                        }
                    }
                    if (!((splitTempLine[i] + "],[").charAt(0) == '[')) {
                        changingLine += "[";
                    }
                    changingLine += splitTempLine[i] + "],";
                    didChange = true;
                }
            }
            if (changingLine.length() > 0) {
                tempFile.set(tempFile.size() - 1, changingLine.substring(0, changingLine.length() - 2) + "]");
            } else {
                tempFile.remove(tempFile.size() - 1);
            }

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
            //e.printStackTrace();
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
                    if (tempFile.size() > 2) {
                        String currentLine = tempFile.get(tempFile.size() - 1);
                        String[] storeLine = currentLine.split(", ");
                        String changingLine = "";
                        if (storeLine[0].equals(store)) {
                            changingLine += storeLine[0] + ", ";
                            String[] sentUsers = storeLine[1].split("; ");
                            boolean found = false;
                            for (int j = 0; j < sentUsers.length; j++) {
                                String[] currentSentUser = sentUsers[j].split(":");
                                if (currentSentUser[0].equals(myUser)) {
                                    changingLine += currentSentUser[0] + ":" + (Integer.parseInt(currentSentUser[1]) + 1);
                                    found = true;
                                } else {
                                    changingLine += sentUsers[j];
                                }
                                if (j < sentUsers.length - 1) {
                                    changingLine += "; ";
                                }
                            }
                            if (!found) {
                                changingLine += "; " + myUser + ":1";
                            }
                            tempFile.set(tempFile.size() - 1, changingLine);
                        }
                    }
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
            //e.printStackTrace();
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
                if (tempFile.size() > 2) {
                    String currentLine = tempFile.get(tempFile.size() - 1);
                    String[] storeLine = currentLine.split(", ");
                    String changingLine = "";
                    if (storeLine[0].equals(store)) {
                        changingLine += storeLine[0] + ", ";
                        String[] sentUsers = storeLine[1].split("; ");
                        boolean found = false;
                        for (int j = 0; j < sentUsers.length; j++) {
                            String[] currentSentUser = sentUsers[j].split(":");
                            if (currentSentUser[0].equals(myUser)) {
                                changingLine += currentSentUser[0] + ":" + (Integer.parseInt(currentSentUser[1]) + 1);
                                found = true;
                            } else {
                                changingLine += sentUsers[j];
                            }
                            if (j < sentUsers.length - 1) {
                                changingLine += "; ";
                            }
                        }
                        if (!found) {
                            changingLine += "; " + myUser + ":1";
                        }
                        tempFile.set(tempFile.size() - 1, changingLine);
                    }
                }
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
            //e.printStackTrace();
            return false;
        }
    }

    public boolean editMessage(int messageToChange, String newMessage) {
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
                        String makeNewLine = "";
                        String lineToChange = tempFile.get(tempFile.size() - 1);
                        makeNewLine += lineToChange.split(", ")[0] + ", " + lineToChange.split(", ")[1] + ", " + lineToChange.split(", ")[2] + ", ";
                        String[] messagesPart = lineToChange.split(", ")[3].split("],\\[");
                        for (int j = 0; j < messageToChange; j++) {
                            if (j > 0) {
                                makeNewLine += "],[";
                            }
                            makeNewLine += messagesPart[j];
                        }
                        makeNewLine += " ";
                        if (!makeNewLine.split(", ")[3].equals(" ")) {
                            makeNewLine = makeNewLine.substring(0, makeNewLine.length() - 1);
                            makeNewLine += "],[";
                        } else {
                            makeNewLine = makeNewLine.substring(0, makeNewLine.length() - 1);
                        }
                        String linePieceToChange = messagesPart[messageToChange];
                        String[] changingLine = linePieceToChange.split(";", 4);
                        changingLine[3] = newMessage;
                        for (int j = 0; j < changingLine.length; j++) {
                            if (j > 0) {
                                makeNewLine += ";";
                            }
                            makeNewLine += changingLine[j];
                        }
                        for (int j = messageToChange + 1; j < messagesPart.length; j++) {
                            makeNewLine += "],[" + messagesPart[j];
                        }
                        if (makeNewLine.charAt(makeNewLine.length() - 1) != ']') {
                            makeNewLine += "]";
                        }
                        tempFile.set(tempFile.size() - 1, makeNewLine);
                    }
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
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMessage(int messageToDelete) {
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
                        String makeNewLine = "";
                        String lineToChange = tempFile.get(tempFile.size() - 1);
                        makeNewLine += lineToChange.split(", ")[0] + ", " + lineToChange.split(", ")[1] + ", " + lineToChange.split(", ")[2] + ", ";
                        String[] messagesPart = lineToChange.split(", ")[3].split("],\\[");
                        for (int j = 0; j < messageToDelete; j++) {
                            if (j > 0) {
                                makeNewLine += "],[";
                            }
                            makeNewLine += messagesPart[j];
                        }
                        makeNewLine += " ";
                        if (!makeNewLine.split(", ")[3].equals(" ")) {
                            makeNewLine = makeNewLine.substring(0, makeNewLine.length() - 1);
                            makeNewLine += "],[";
                        } else {
                            makeNewLine = makeNewLine.substring(0, makeNewLine.length() - 1);
                        }
                        String linePieceToChange = messagesPart[messageToDelete];
                        String[] changingLine = linePieceToChange.split(";", 4);
                        changingLine[1] = "deleted";
                        for (int j = 0; j < changingLine.length; j++) {
                            if (j > 0) {
                                makeNewLine += ";";
                            }
                            makeNewLine += changingLine[j];
                        }
                        for (int j = messageToDelete + 1; j < messagesPart.length; j++) {
                            makeNewLine += "],[" + messagesPart[j];
                        }
                        if (makeNewLine.charAt(makeNewLine.length() - 1) != ']') {
                            makeNewLine += "]";
                        }
                        tempFile.set(tempFile.size() - 1, makeNewLine);
                    }
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
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public String[] getMessages() {
        return messages;
    }

}
