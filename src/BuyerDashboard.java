import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
public class BuyerDashboard {
    public ArrayList<String> readFile() {
        ArrayList<String> sellers = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("Seller.txt")));
            String line = bfr.readLine();
            while (line != null) {
                sellers.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellers;
    }






}
