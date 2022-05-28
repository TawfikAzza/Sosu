package bll.exceptions;

import be.User;
import bll.util.CheckInput;

public class UserException extends Throwable {
    private String exceptionMessage;
    private String instructions;

    public UserException(String exceptionMessage, Exception exception) {
        //System.out.println( exceptionMessage+"\n" + exception);
        this.exceptionMessage = exceptionMessage;
    }

    public UserException() {
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

    public void checkUserFN(String fn) throws UserException {
        if (fn.isEmpty())
            throw new UserException("Please enter your first name.", new Exception());

        if (!(fn.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            UserException userException = new UserException("Please find a valid first name", new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;
        }
    }

    public void checkUserLN(String ln) throws UserException {

        if (ln.isEmpty())
            throw new UserException("Please enter your last name.", new Exception());

        if (!(ln.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            UserException userException = new UserException("Please find a valid last name", new Exception());
            userException.setInstructions("A correct name is only composed of Alphabet characters");
            throw userException;
        }
    }

    public void checkUserUN(String un) throws UserException {
        if (un==null||un.isEmpty())
            throw new UserException("Please find a username.", new Exception());
    }

    public void checkUserUName(String un, int userNameTaken) throws UserException {
        if (userNameTaken > 0) {
            UserException userException = new UserException("user name already exists.", new Exception());
            userException.setInstructions("Please find another one and try again.");
            throw userException;
        } else {
            if (userNameTaken > 1) {
                UserException userException = new UserException("user name already exists.", new Exception());
                userException.setInstructions("Please find another one and try again.");
                throw userException;
            }
        }
    }

    public void checkUserPassword(String passWord) throws UserException {
        if (!CheckInput.isPasswordValid(passWord)) {
            UserException userException = new UserException("Please find a correct password.", new Exception());
            userException.setInstructions("A password must contain at least one digit [0-9],one lowercase Latin character [a-z],one uppercase Latin character [A-Z],one special character like ! @ # & ( ) and at least 8 characters.");
            throw userException;
        }
    }

    public void checkEmail(String email) throws UserException {
        if (email.isEmpty())
            throw new UserException("Please enter your email.", new Exception());

        if (!CheckInput.isValidEmailAddress(email))
            throw new UserException("Please enter a valid email.", new Exception());
    }

    public void checkPhoneNumber(String phoneNumber) throws UserException {
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException numberFormatException) {
            UserException userException = new UserException("Please enter a valid number", new Exception());
            userException.setInstructions("A valid number is composed of 8 digits.");
            throw userException;
        }
        if (phoneNumber.length() != 8) {
            UserException userException = new UserException("Please enter a valid number", new Exception());
            userException.setInstructions("A valid number is composed of 8 digits.");
            throw userException;
        }
    }
}