package gui.Controller;

import be.Case;
import bll.exceptions.CaseException;
import gui.Model.CaseModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CaseCreationController {

    private CaseModel caseModel;

    @FXML
    private TextField txtFieldCaseName;
    @FXML
    private TextArea txtAreaContent;

    public CaseCreationController() {
        try {
            this.caseModel = new CaseModel();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleSubmitClick(ActionEvent actionEvent) {
        String caseName = txtFieldCaseName.getText();
        String content = txtAreaContent.getText();

        Case newCase = new Case(caseName, content);
        try {
            caseModel.addCase(newCase);
            Stage stage = (Stage) txtFieldCaseName.getScene().getWindow();
            stage.close();
        } catch (CaseException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) txtFieldCaseName.getScene().getWindow();
        stage.close();
    }
}
