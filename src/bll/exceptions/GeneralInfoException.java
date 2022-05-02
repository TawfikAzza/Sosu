package bll.exceptions;

public class GeneralInfoException extends Throwable {

    private String message;
    public GeneralInfoException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
