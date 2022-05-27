package gui.Controller;

import be.Case;
import be.Citizen;
import bll.exceptions.CaseException;
import bll.util.GlobalVariables;
import gui.Model.CaseModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaseController implements Initializable {
    private CaseModel caseModel;
    private Citizen selectedCitizen;
    @FXML
    private Label labelCase;
    @FXML
    private Label labelCaseName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.caseModel = new CaseModel();
            this.selectedCitizen = GlobalVariables.getSelectedCitizen();
            Case citCase = caseModel.getCitizenCase(selectedCitizen);
            labelCase.setText(citCase.getContent());
            labelCaseName.setText(citCase.getCaseName());
        } catch (IOException | CaseException e) {
            DisplayMessage.displayError(e);
        }

    }
}
