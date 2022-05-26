package gui.Controller;

import be.Case;
import bll.exceptions.CaseException;
import bll.util.GlobalVariables;
import gui.Model.CaseModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CaseViewController implements Initializable {
    @FXML
    private TextArea contentArea;

    @FXML
    private TextField caseNameField;

    private CaseModel caseModel;
    private Case currentCase;

    public CaseViewController(){
        try {
            caseModel = new CaseModel();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        Button eventSource = ((Button) event.getSource());
        ((Stage) eventSource.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindSizes();
        try {
            getCase();
        } catch (CaseException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    private void getCase() throws CaseException {
        currentCase = caseModel.getCitizenCase(GlobalVariables.getSelectedCitizen());
        if (currentCase==null)
            return;
        caseNameField.setText(currentCase.getCaseName());
        contentArea.setText(currentCase.getContent());
    }

    private void bindSizes() {
        HBox contentParent = (((HBox) contentArea.getParent()));
        contentArea.prefWidthProperty().bind(contentParent.widthProperty());
        contentArea.prefHeightProperty().bind(contentParent.heightProperty());
        HBox nameParent = (((HBox) caseNameField.getParent()));
        caseNameField.prefWidthProperty().bind(nameParent.widthProperty());
        caseNameField.prefHeightProperty().bind(nameParent.heightProperty());
    }
}
