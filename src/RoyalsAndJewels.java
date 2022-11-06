package src;

import java.util.Scanner;

public class RoyalsAndJewels {

    private String username = "";  // seller or buyer
    private static String fileName="";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);


        //add intro sequences code;

    }

    public static void sellerInitiate(String fileName, String userName, Scanner scan){
        Seller seller = new Seller(fileName);
        SellerDashboard sd = new SellerDashboard(scan, userName, fileName);


    }
    public static void buyerInitiate(String username){

    }
}
