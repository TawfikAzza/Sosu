package gui.Controller;

import bll.exceptions.CitizenException;
import gui.Model.CitizenModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAdditionalInfoController implements Initializable {

    @FXML
    private Tab generalInfoTab;

    @FXML
    private Tab healthConditionTab;

    @FXML
    private Tab functionalAbilityTab;

    private CitizenModel citizenModel;


    @FXML
    void handleFinishEditing(ActionEvent event) {
        closeThisWindow(event);
    }

    private void closeThisWindow(ActionEvent event) {
        Button actionSource = ((Button) event.getSource());
        Scene currentScene = actionSource.getScene();
        Stage currentStage = ((Stage) currentScene.getWindow());
        try {
            citizenModel.refreshTables();
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
        }
        currentStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.citizenModel = CitizenModel.getInstance();
            loadFAbilityContent();
            loadHConditionContent();
            loadGInfoContent();
        } catch (IOException | CitizenException e) {
            DisplayMessage.displayError(e);
        }
    }

    private void loadGInfoContent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/GeneralInfoReportView.fxml"));
        generalInfoTab.setContent(root);
    }

    private void loadFAbilityContent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/FunctionalSectionDisplay.fxml"));
        functionalAbilityTab.setContent(root);
    }

    private void loadHConditionContent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/HealthSectionDisplay.fxml"));
        healthConditionTab.setContent(root);
    }
}
