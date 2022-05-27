package bll.util;


import be.ObservationType;

import java.time.LocalDate;
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

    /**
     * Password must contain at least one digit [0-9].
     * Password must contain at least one lowercase Latin character [a-z].
     * Password must contain at least one uppercase Latin character [A-Z].
     * Password must contain at least one special character like ! @ # & ( ).
     * Password must contain a length of at least 8 characters and a maximum of 20 characters.
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isDateBeforeToday(String dateString){
        LocalDate date = DateUtil.parseDate_GUI(dateString);
        if (LocalDate.now().isAfter(date))
            return true;
        return false;

    }

    public static boolean isValidMeasurement(ObservationType observationType, float measurement){
        boolean isValid=true;
        switch (observationType)
        {
            case BPMeasurement -> isValid= measurement>=0 && measurement<=300;
            case BSMeasurement -> isValid= measurement>=0 && measurement<=7;
            case TempMeasurement -> isValid= measurement>=32 && measurement<=45;
            case WeightMeasurement -> isValid= measurement>=10 && measurement<=300;
            case OxyMeasurement -> isValid= measurement>=0 && measurement<=100;
        }
        return isValid;
    }
}
