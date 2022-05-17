package bll.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_PATTERN_GUI = "dd/MM/yyyy";

    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final DateTimeFormatter DATE_FORMATTER_GUI =
            DateTimeFormatter.ofPattern(DATE_PATTERN_GUI);

    public static String formatDateTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static String formatDateGui(LocalDate date) {
        if(date ==null){
            return null;
        }
        return DATE_FORMATTER_GUI.format(date);
    }

    public static LocalDateTime parseDateTime(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static LocalDate parseDate(String dateString) {
        try {
            if(dateString==null)
                System.out.println("DATE IS NULL");
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LocalDate parseDate_GUI(String dateString) {
        LocalDate formattedDate = null;
        if(dateString.isEmpty() || dateString.isBlank())
            return formattedDate;
        try {
            formattedDate = DATE_FORMATTER_GUI.parse(dateString, LocalDate::from);
        }
        catch (DateTimeParseException e){
            return formattedDate;
        }

        return formattedDate;
    }
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parseDate_GUI(dateString) != null;
    }
}