package src;

import java.util.ArrayList;
import java.util.Scanner;

public class IntroSequences {

    //  Format:
    //    username, password, buyer
    //    username, password, seller, store


    // I am become procrastination, destroyer of grades.


    public IntroSequences() {
        Buyer b = new Buyer("src/SampleDatabase.txt");
        Seller se = new Seller("src/SampleDatabase.txt");
        System.out.println("To login, enter 1. \nTo create an account, enter 2. ");
        Scanner s = new Scanner(System.in);
        s.nextLine();
        if (s.nextInt() == 1) {
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
                System.out.println("buyer success");
            else if (login == 2)
                System.out.println("seller success");
            else
                System.out.println("failure");

        }
        else if (s.nextInt() == 2) {
            s.nextLine();
            System.out.println("If you want to make a buyer account, enter 1. \nIf you are want to make a " +
                    "seller account, enter 2.");
            int buyer = s.nextInt();
            s.nextLine();

            String finalUser;
            String finalPass;

            if (buyer == 1) {
                System.out.println("Please enter your new username: ");
                String usernameNew = s.nextLine();
                while (!((boolean) verify(usernameNew, false, false)[1])) {
                    System.out.println("That username is taken! ");
                }
                finalUser = (String) verify(usernameNew, false, false)[0];

                System.out.println("Please enter your new password: ");
                String passwordNew = s.nextLine();
                while (!((boolean) verify(passwordNew, false, true)[1])) {
                    System.out.println("That password is taken! ");
                }
                finalPass = (String) verify(passwordNew, false, true)[0];
            }
            else if (buyer == 2) {
                System.out.println("Please enter your new username: ");
                String usernameNew = s.nextLine();
                while (!((boolean) verify(usernameNew, true, false)[1])) {
                    System.out.println("That username is taken! ");
                }
                finalUser = (String) verify(usernameNew, true, false)[0];

                System.out.println("Please enter your new password: ");
                String passwordNew = s.nextLine();
                while (!((boolean) verify(passwordNew, true, true)[1])) {
                    System.out.println("That password is taken! ");
                }
                finalPass = (String) verify(passwordNew, true, true)[0];
            }
        }
    }

    public static void main(String[] args) {
        IntroSequences is = new IntroSequences();
    }

    public Object[] verify(String test, boolean seller, boolean password) {
        Buyer b = new Buyer("src/SampleDatabase.txt");
        Seller se = new Seller("src/SampleDatabase.txt");
        boolean success = false;

        if (!seller) {
            ArrayList<String> takenLines = b.findBuyers();
            for (String i : takenLines) {
                if (!password && i.split(",")[0].equals(test)) {
                    return new Object[]{test, false};
                } else if (password && i.split(",")[1].equals(test)) {
                    return new Object[]{test, false};
                }
            }
            return new Object[]{test, true};
        }
        else if (seller) {
            ArrayList<String> takenLines = b.findSellers();
            for (String i : takenLines) {
                if (!password && i.split(",")[0].equals(test)) {
                    return new Object[]{test, false};
                } else if (password && i.split(",")[1].equals(test)) {
                    return new Object[]{test, false};
                }
            }
            return new Object[]{test, true};
        }

        return new Object[0];
    }

//  public static void test() {
//
//      for (String i : b.findSellers())
//          System.out.println(i);
//      for (String i : s.findBuyers())
//          System.out.println(i);
//
//  }

}
