import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Buyer {
    public final boolean buyer = true;
    public final boolean seller = false;

    public String username;
    public String email;
    public String file;
    public BufferedReader br;
    public BufferedWriter bw;




    public Buyer(String file) {
        try {
            this.file = file;
            this.br = new BufferedReader(new FileReader(file));
            this.bw = new BufferedWriter(new FileWriter(file));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> findSellers() {
        ArrayList<String> sellers = new ArrayList<String>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (checkSeller(line))
                    sellers.add(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sellers;
    }

    public boolean checkSeller(String str) {
        if (Objects.equals(str.split(",")[2], "seller"))
            return true;
        else
            return false;
    }
    public boolean isSeller() {
        return seller;
    }
    public boolean isBuyer() {
        return buyer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }
}