package gui.Controller;

import bll.util.GlobalVariables;
import gui.utils.DisplayMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenInfoControlsController {
    public void openAbilities() {
        if(GlobalVariables.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/FunctionalSectionDisplay.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void openHealthCondition() {
        if(GlobalVariables.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/HealthSectionDisplay.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void openGeneralInfo() {
        if(GlobalVariables.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/GeneralInfoReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void openMedicinelist() {
        if(GlobalVariables.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/MedicineListView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void openObservation() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/Observations.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
