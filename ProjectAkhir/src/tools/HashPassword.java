/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author mfaja
 */
public class HashPassword {
    int key = 6;
    String input;

    public HashPassword(String input) {
        this.input = input;
    }
    public HashPassword() {
        
    }
    
    
    public String encrypt(String mess){
        try {
  
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
  
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(mess.getBytes());
  
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
  
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
  
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    void undo(String hash){

        String hasil = "e39b9c178b2c9be4e99b141d956c6ff6";
        if(hasil.equals(hash)){
            System.out.println("berhasil");
        } else {
            System.out.println("gagal");
        }
        
    }
    
//    public char encrypt(){
//        char[] chars = input.toCharArray();
//        char result;
//        String hasil;
//        for (char c : chars){
//            c += key;
////            System.out.println(c);
//            result += c;
//            hasil = String.valueOf(result);
//            return c;
//        }
//    }
    
    void decrypt(){
        char[] chars = input.toCharArray();
        System.out.println("Hasil : ");
        for (char c : chars){
            c -= key;
            System.out.print(c);
            
            
        }
        System.out.println("");
        System.out.println("Awal : " + input);
    }
    
}
