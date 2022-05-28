package gui.Controller;

import bll.util.GlobalVariables;
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



    @FXML
    void handleFinishEditing(ActionEvent event) {
        closeThisWindow(event);
    }

    @FXML
    private void closeThisWindow(ActionEvent event) {
        Button actionSource = ((Button) event.getSource());
        Scene currentScene = actionSource.getScene();
        Stage currentStage = ((Stage) currentScene.getWindow());
        currentStage.close();
        GlobalVariables.setSelectedCitizen(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFAbilityContent();
            loadHConditionContent();
            loadGInfoContent();
        } catch (IOException e) {
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
