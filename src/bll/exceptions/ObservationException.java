package bll.exceptions;

import be.ObservationType;

public class ObservationException extends Throwable{
    String exceptionMessage;
    String instructions;
    public ObservationException(String exceptionMessage, Exception exception) {
        //System.out.println( exceptionMessage+"\n" + exception);
        this.exceptionMessage = exceptionMessage;
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


    public static void isValidMeasurement(float measurement,float minValue,float maxValue) throws ObservationException {
        if (measurement>maxValue || measurement<minValue){
        ObservationException observationException = new ObservationException("Invalid input",new Exception());
        observationException.setInstructions("Please find a number between "+ (int) minValue+ " and "+(int)maxValue );
        throw observationException;
        }
    }
}
