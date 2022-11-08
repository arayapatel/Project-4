package src;

import java.util.Scanner;

public class IntroSequences {
  
  //  Format:
  //    username, password, buyer
  //    username, password, seller, store


    public static void main(String[] args) {
        test();
    }
  
  public IntroSequences() {
      System.out.println("To login, enter 1. \nTo create an account, enter 2. ");
      Scanner s = new Scanner(System.in);
      if (s.nextInt() == 1) {
          s.nextLine();
          System.out.println("Please enter username: ");
          String username = s.nextLine();
          System.out.println("Please enter password: ");
          String password = s.nextLine();

      }
  }

  public static void test() {
      Buyer b = new Buyer("src/SampleDatabase.txt");
      Seller s = new Seller("src/SampleDatabase.txt");

      for (String i : b.findSellers())
          System.out.println(i);
      for (String i : s.findBuyers())
          System.out.println(i);

  }

}
