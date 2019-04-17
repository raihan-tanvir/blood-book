package com.example.raihan.blood;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static String textPatternError="Only letters are allowed with minimum 1 to max 15 character";
    public static String emailPatternError="Please enter a valid email, eg: example@blood.com";
    public static String passwordPatternError="only alphanumeric characters are allowed with at least 1 digit & 1 character(@$!%*?&) & 1 special character and 6 t0 25 character long";

   // public static String passwordPatternError="only alphanumeric characters are allowed with at least 1 digit & 1 character and 6 characters";
    public static String phonePatternError="valid format +880XXXXXXXXXX or 0XXXXXXXXXX";

    // validating name
    public static boolean isValidText(String text) {
        String NAME_PATTERN = "[A-Za-z\\s]{1,15}";

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    // validating email
    public static  boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    public  static boolean isValidPassword(String pass) {

        String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[@$!%*?&])(?=.*[a-zA-z]).{6,25}";

      //  String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-zA-z]).{6,25}";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public static  boolean isValidPhone(String phone) {
        String PHONE_PATTERN = "^((\\+880)|0)(\\d){10}$";

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}
