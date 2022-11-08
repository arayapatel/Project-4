package src;

public class IntroSequences {
  
  //  Format:
  //    username, password, buyer
  //    username, password, seller, store


    public static void main(String[] args) {
        Buyer b = new Buyer("src/SampleDatabase.txt");
        Seller s = new Seller("src/SampleDatabase.txt");
    }
  
  public IntroSequences() {
    
  }

}
