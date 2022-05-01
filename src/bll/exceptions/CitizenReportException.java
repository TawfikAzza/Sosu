package bll.exceptions;

public class CitizenReportException extends Throwable{
    private String message;
    public CitizenReportException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
