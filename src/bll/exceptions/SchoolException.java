package bll.exceptions;

public class SchoolException extends Throwable{
    String exceptionMessage;
    public SchoolException(String exceptionMessage,Exception exception){
        //System.out.println( exceptionMessage+"\n" + exception);
        this.exceptionMessage=exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

}
