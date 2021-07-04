package net.drs.myapp.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppUtils {

    public static String generateRandomString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static Boolean enableAccountExpiry() {
        return false;
    }

    public static String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static int getAccountValidityExpiryAfterDays() {
        // get the value 30 from Database
        return 30;
    }

    // after 10 mins, the activation string is no more valid
    public static long getActivationStringExpiryTimeInMilliseconds() {
        return 600000L;
    }
    
    public static boolean isPhoneNumber(String enteredValue) {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}"); 
        Matcher m = p.matcher(enteredValue); 
        return (m.find() && m.group().equals(enteredValue));
    }

    public static boolean isEmailId(String enteredValue) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return enteredValue.matches(regex);
        
    }
    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
        
    }
    
    
    public static Date getCurrentDate() {
        java.util.Date uDate = new java.util.Date();
        return new java.sql.Date(uDate.getTime());
    }
 
    
     public static Date getAccountValidFor100Years() {
         java.util.Date uDate = new java.util.Date();
         uDate.setYear(2120);
         return new java.sql.Date(uDate.getTime());
     }
     
     
     public static char[] fourDigitOTPForMobileVerification() {
         
         int len = 4;
         String numbers = "0123456789"; 
         
         // Using random method 
         Random rndm_method = new Random(); 
   
         char[] otp = new char[len]; 
   
         for (int i = 0; i < len; i++) 
         { 
             // Use of charAt() method : to get character value 
             // Use of nextInt() as it is scanning the value as int 
             otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
         } 
         return otp; 
         
     }
}
