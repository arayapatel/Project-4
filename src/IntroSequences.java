package src;

import com.sun.jdi.InvalidTypeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IntroSequences {

    //  Format:
    //    username, password, buyer
    //    username, password, seller, stores
    //    stores format: Store1; Store2; Store 3


    // I am become procrastination, destroyer of grades.

    private String username;
    private String password;
    private String type;

    public IntroSequences() {
        try {
            Buyer b = new Buyer("account_list.txt");
            Seller se = new Seller("account_list.txt");
            System.out.println("To login, enter 1. \nTo create an account, enter 2. ");
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter("account_list.txt", true)));
            PrintWriter pwSeller = new PrintWriter(new BufferedWriter(
                    new FileWriter("src/Seller.txt", true)));
            PrintWriter pwBuyer = new PrintWriter(new BufferedWriter(
                    new FileWriter("src/Buyer.txt", true)));
            Scanner s = new Scanner(System.in);
            int create = s.nextInt();
            s.nextLine();

            if (create == 1) {
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
                            this.username = username;
                            this.password = password;
                            break;
                        }
                    }
                } else if (buyer == 2) {
                    ArrayList<String> sellers = b.findSellers();

                    for (String i : sellers) {
                        if (i.split(",")[0].equals(username) && i.split(",")[1].equals(password)) {
                            login = 2;
                            this.username = username;
                            this.password = password;
                            break;
                        }
                    }
                }
                if (login == 1) {
                    System.out.println("Successfully logged in as a buyer! ");
                    this.type = "Buyer";
                }
                else if (login == 2) {
                    System.out.println("Successfully logged in as a seller! ");
                    this.type = "Seller";
                }
                else {
                    System.out.println("Failure to log in. \n");
                    IntroSequences is = new IntroSequences();
                }


            } else if (create == 2) {
                System.out.println("If you want to make a buyer account, enter 1. \nIf you are want to make a " +
                        "seller account, enter 2.");
                int buyer = s.nextInt();
                s.nextLine();

                if (buyer == 1) {
                    this.type = "Buyer";
                } else if (buyer == 2) {
                    this.type = "Seller";
                }

                System.out.println("Please enter your new username: ");
                String usernameNew = s.nextLine();
                boolean go = false;
                while (!go) {
                    if (!usernameNew.contains(","))
                        go = true;
                    else {
                        System.out.println("Invalid username! ");
                        System.out.println("Please enter another username. ");
                        usernameNew = s.nextLine();
                    }
                    while (!((boolean) verify(usernameNew)[1])) {
                        System.out.println("That username is taken! ");
                        System.out.println("Please enter another username. ");
                        usernameNew = s.nextLine();
                    }
                }
                username = (String) verify(usernameNew)[0];
                boolean goPass = false;
                System.out.println("Please enter your new password: ");
                password = s.nextLine();
                while (!goPass) {
                    if (password.contains(",")) {
                        System.out.println("Invalid password! ");
                        System.out.println("Please enter another password. ");
                        password = s.nextLine();
                    }
                    else
                        goPass = true;
                }

                String store = "";
                String stores = "";
                if (buyer == 2) {
                    System.out.println("What store(s) do you represent? " +
                            "If multiple, please enter them separated by commas.");
                    store = s.nextLine();
                    for (char i : store.toCharArray()) {
                        if (i == ',')
                            stores += ",";
                        else
                            stores += i;
                    }
                }
                pw.println();
                if (buyer == 1) {
                    pw.write(username + "," + password + ",buyer");
                    pwBuyer.println();
                    pwBuyer.write(username + "; " + 0);
                    pwBuyer.flush();
                }
                else if (buyer == 2) {
                    pw.write(username + "," + password + ",seller," + stores);
                    pwSeller.println();
                    pwSeller.write(username + "; " + stores + ": " + 0);
                    pwSeller.flush();
                }
                pw.flush();
                System.out.println("Account created successfully! ");
            }
        } catch (InputMismatchException ime) {
            new IntroSequences();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again. ");
        }
    }

    public static void main(String[] args) {
        IntroSequences is = new IntroSequences();
    }

    public Object[] verify(String test) {
        Buyer b = new Buyer("account_list.txt");
        Seller se = new Seller("account_list.txt");
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

    public String getType() {
        return type;
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


