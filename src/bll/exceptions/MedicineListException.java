package bll.exceptions;

public class MedicineListException extends Throwable {

    private String message;
    public MedicineListException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
