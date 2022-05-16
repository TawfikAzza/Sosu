package bll.exceptions;


public class GIReportException extends Throwable  {

    private String message;
    public GIReportException (String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}


