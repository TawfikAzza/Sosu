package bll.exceptions;

import be.User;

public class UserException extends Throwable {
    String exceptionMessage;
    String instructions;
    public UserException(String exceptionMessage,Exception exception){
        //System.out.println( exceptionMessage+"\n" + exception);
        this.exceptionMessage=exceptionMessage;
    }

    public UserException(){
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

    public void checkUserFN(String fn)throws UserException {
        if (fn.isEmpty())
            throw new UserException("Please enter your first name.", new Exception());

        if (!(fn.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            UserException userException = new UserException("Please find a valid first name", new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;
        }
    }

    public void checkUserLN(String ln)throws UserException {

        if (ln.isEmpty())
            throw new UserException("Please enter your last name.", new Exception());

        if (!(ln.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
        UserException userException = new UserException("Please find a valid last name", new Exception());
        userException.setInstructions("A correct name is only composed of Alphabet characters");
        throw userException;
    }}
}