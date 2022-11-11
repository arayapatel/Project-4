package src;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class IntroSequences {

    //  Format:
    //    username, password, buyer
    //    username, password, seller, store


    // I am become procrastination, destroyer of grades.

    private String username;
    private String password;

    public IntroSequences() {
        try {
            Buyer b = new Buyer("src/SampleDatabase.txt");
            Seller se = new Seller("src/SampleDatabase.txt");
            System.out.println("To login, enter 1. \nTo create an account, enter 2. ");
            PrintWriter pw = new PrintWriter(new File("account_list.txt"));
            Scanner s = new Scanner(System.in);
            int create = s.nextInt();
            s.nextLine();

            if (create == 1) {
                s.nextLine();
                System.out.println("If you are a buyer, enter 1. \nIf you are a seller, enter 2.");
                int buyer = s.nextInt();
                s.nextLine();
                System.out.println("Please enter username: ");
                String username = s.nextLine();
                System.out.println("Please enter password: ");
                String password = s.nextLine();

                int login = 0;


                if (buyer == 1) {
                    ArrayList<String> buyers = se.findBuyers();

                    for (String i : buyers) {
                        if (i.split(",")[0].equals(username) && i.split(",")[1].equals(password)) {
                            login = 1;
                            break;
                        }
                    }
                } else if (buyer == 2) {
                    ArrayList<String> sellers = b.findSellers();

                    for (String i : sellers) {
                        if (i.split(",")[0].equals(username) && i.split(",")[1].equals(password)) {
                            login = 2;
                            break;
                        }
                    }
                }
                if (login == 1)
                    System.out.println("Successfully logged in as a buyer! ");
                else if (login == 2)
                    System.out.println("Successfully logged in as a seller! ");
                else {
                    System.out.println("Failure to log in. \n");
                    IntroSequences is = new IntroSequences();
                }


            } else if (create == 2) {
                System.out.println("If you want to make a buyer account, enter 1. \nIf you are want to make a " +
                        "seller account, enter 2.");
                int buyer = s.nextInt();
                s.nextLine();

                System.out.println("Please enter your new username: ");
                String usernameNew = s.nextLine();
                while (!((boolean) verify(usernameNew)[1])) {
                    System.out.println("That username is taken! ");
                    System.out.println("Please enter another username. ");
                    usernameNew = s.nextLine();
                }
                username = (String) verify(usernameNew)[0];

                System.out.println("Please enter your new password: ");
                password = s.nextLine();
                String store = "";
                if (buyer == 2) {
                    System.out.println("What store do you represent? ");
                    store = s.nextLine();
                }

                System.out.println("Account created successfully! ");
                if (buyer == 1)
                    pw.write(username + "," + password + ",buyer");
                if (buyer == 2)
                    pw.write(username + "," + password + ",seller," + store);
                pw.flush();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again. ");
        }
    }

    public static void main(String[] args) {
        IntroSequences is = new IntroSequences();
    }

    public Object[] verify(String test) {
        Buyer b = new Buyer("src/SampleDatabase.txt");
        Seller se = new Seller("src/SampleDatabase.txt");
        ArrayList<String> takenLines = b.findBuyers();
        takenLines.addAll(se.findSellers());
        for (String i : takenLines) {
            if (i.split(",")[0].equals(test))
                return new Object[]{test, false};

        }
        return new Object[]{test, true};
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

//  public static void test() {
//
//      for (String i : b.findSellers())
//          System.out.println(i);
//      for (String i : s.findBuyers())
//          System.out.println(i);
//
//  }


