package bll.exceptions;

public class CitizenException extends Throwable {

    private String message;
    public CitizenException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
