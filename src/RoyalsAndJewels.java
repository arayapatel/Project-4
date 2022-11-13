package src;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RoyalsAndJewels {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        IntroSequences is = new IntroSequences();
        boolean repeat = true;
        while (repeat) {
            if (is.getType().equals("Seller")) {
                SellerDashboard sd = new SellerDashboard(scan, is.getUsername(), is.getUsername().trim() + ".txt");
            } else if (is.getType().equals("Buyer")) {
                BuyerDashboard bd = new BuyerDashboard(scan, is.getUsername(), is.getUsername().trim() + ".txt");
            }
            while (true) {
                System.out.println("Would you like to log in again (Type 1) or leave the program? (Type 2)");
                String s = scan.nextLine();
                if (s.trim().equals("1")) {
                    repeat = true;
                    break;
                } else if (s.trim().equals("2")) {
                    repeat = false;
                    break;
                }
            }
        }
        System.out.println("Good Bye. Spend some more money and sell more rich people things next time!");
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


