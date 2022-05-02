package gui.utils;

import javafx.scene.control.Alert;
/***
 * Class which will hold and manage the displaying of error message
 * sent by the Controller of the MVC.
 * These methods are static so they can be called without instanciation of an object for ease of use.
 * **/
public class DisplayMessage {

    public static void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    public static void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You are missing data");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
