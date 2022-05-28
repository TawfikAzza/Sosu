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

public class CaseCreationController{
    private CitizenFormController citizenFormController;
    private CaseModel caseModel;
    private boolean editing;
    private Case citCase;

    @FXML
    private TextField txtFieldCaseName;
    @FXML
    private TextArea txtAreaContent;

    public CaseCreationController(CitizenFormController controller, boolean editing) {
        try {
            this.caseModel = new CaseModel();
            this.citizenFormController = controller;
            this.editing = editing;
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }


    @FXML
    private void handleSubmitClick(ActionEvent actionEvent) {
        String caseName = txtFieldCaseName.getText();
        String content = txtAreaContent.getText();
        Case newCase = new Case(caseName, content);
        if(!editing) {
            try {
                int caseID = caseModel.addCase(newCase);
                citizenFormController.setCaseID(caseID);
                Stage stage = (Stage) txtFieldCaseName.getScene().getWindow();
                stage.close();
            } catch (CaseException e) {
                DisplayMessage.displayError(e);
            }
        }
        if(editing)
        {
            caseModel.editCase(citCase.getId(), newCase);
            Stage stage = (Stage) txtFieldCaseName.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) txtFieldCaseName.getScene().getWindow();
        stage.close();
    }

    private void fillFields()
    {
        txtFieldCaseName.setText(citCase.getCaseName());
        txtAreaContent.setText(citCase.getContent());
    }

    public void setCitCase(Case citCase) {
        this.citCase = citCase;
        if(editing)
        {
            fillFields();
        }
    }
}
