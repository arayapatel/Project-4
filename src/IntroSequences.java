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
        if (s.nextInt() == 1) {
            s.nextLine();
            System.out.println("If you are a buyer, enter 1. If you are a seller, enter 2.");
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
    }

    public static void main(String[] args) {
        IntroSequences is = new IntroSequences();
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
