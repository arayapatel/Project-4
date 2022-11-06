package src;
<<<<<<< HEAD

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
public class SellerDashboard {
    public ArrayList<String> readFile() {
        ArrayList<String> buyers = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Buyer.txt")));
            String line = bfr.readLine();
            while (line != null) {
                buyers.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buyers;
    }

=======
>>>>>>> 10efa4ad27a58965665292f6870e2b20d862cbce

public class SellerDashboard extends Dashboard{
    public SellerDashboard(){

    }
}
