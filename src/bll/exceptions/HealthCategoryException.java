package bll.exceptions;

public class HealthCategoryException extends Throwable{

    private String message;
    public HealthCategoryException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}