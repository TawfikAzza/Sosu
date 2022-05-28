package bll.exceptions;

public class SchoolException extends Throwable{
    private String exceptionMessage;
    private String instructions;
    public SchoolException(String exceptionMessage,Exception exception){
        //System.out.println( exceptionMessage+"\n" + exception);
        this.exceptionMessage=exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
