package src;

public class IntroSequences {
  
  //  Format:
  //    username, password, buyer
  //    username, password, seller, store


    public static void main(String[] args) {
        test();
    }
  
  public IntroSequences() {
      System.out.printf("sample");
    
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
