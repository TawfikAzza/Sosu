package bll.exceptions;

public class CitizenStudentRelationException extends Throwable{

    private String message;

    public CitizenStudentRelationException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
