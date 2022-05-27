package gui.utils;

import bll.exceptions.ObservationException;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/***
 * Class which will hold and manage the displaying of error message
 * sent by the Controller of the MVC.
 * These methods are static so they can be called without instanciation of an object for ease of use.
 * **/
public class DisplayMessage {

    public static void displayError(Throwable t) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong...");
                alert.setHeaderText(t.getMessage());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("error-dialog");
                ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/error_icon.png"));
                alert.showAndWait();
            }
        });
    }
    public static void displayMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,message, ButtonType.OK);
                alert.setTitle("You are missing data");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("info-dialog");
                ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/info_icon.png"));
                alert.showAndWait();
            }
        });
    }

    public static ButtonType displayConfirmation(String title,String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,header,ButtonType.OK,ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(title);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("confirmation-dialog");
        ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/confirmation_icon.png"));
        return alert.showAndWait().get();
    }
    public static void displayErrorMessage(UserException ue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong...");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("error-dialog");
                ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/error_icon.png"));
                alert.showAndWait();
            }
        });
    }

    public static void displaySchoolErrorMessage(SchoolException se) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong...");
                alert.setContentText(se.getExceptionMessage());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("error-dialog");
                ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/error_icon.png"));
                alert.showAndWait();
            }
        });
    }

    public static void displayObservationErrorMessage(ObservationException oe) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong...");
                alert.setContentText(oe.getInstructions());
                alert.setHeaderText(oe.getExceptionMessage());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("error-dialog");
                ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image("Images/error_icon.png"));
                alert.showAndWait();
            }
        });
    }
}
