package bll.exceptions;

public class AbilityCategoryException extends Throwable{

    private String message;
    public AbilityCategoryException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}