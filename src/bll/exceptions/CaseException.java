package bll.exceptions;

public class CaseException extends Throwable{
    private String message;
    public CaseException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
