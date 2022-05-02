package bll.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInput {
    public static boolean isValidEmailAddress(String email) {
        boolean stricterFilter = true;
        String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
        String emailRegex = stricterFilter ? stricterFilterString : laxString;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isPasswordValid(String password) {
        int digitCounter = 0;

        if (password.length() >= 10 ) {
            for(int index = 0; index < password.length(); index++) {
                char passChar = password.charAt(index);
                if (!Character.isLetterOrDigit(passChar)) {
                    return false;
                }
                else {
                    if (Character.isDigit(passChar)) {
                        digitCounter++;
                    }
                }
            }
        }
        return digitCounter >= 2;
    }

    public boolean isValidName(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return !m.find();
    }
}
