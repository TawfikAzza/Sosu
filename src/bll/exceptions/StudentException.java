package bll.exceptions;

public class StudentException extends Throwable {
    private String message;
    public StudentException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
