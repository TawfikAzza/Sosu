package gui.Controller;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import bll.exceptions.CitizenException;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalCitizen;
import gui.Model.GInfoModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GeneralInfoFormController {

    @FXML
    private Label infoCategoryNameLabel;

    @FXML
    private Label definitionLabel;

    @FXML
    private TextField infoContentField;

    private InfoCategory selectedInfoCategory;

    GInfoModel model;

    public GeneralInfoFormController() {
        try {
            model = new GInfoModel();
        } catch (GeneralInfoException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (checkFields()){
            closeWindow(event);
            saveInformation();
        }
    }

    private void closeWindow(ActionEvent event) {
        Button sourceButton = ((Button) event.getSource());
        ((Stage) sourceButton.getParent().getScene().getWindow()).close();
    }

    private void saveInformation() {
        saveInformationThread.start();
    }

    private boolean checkFields() {
        String popupMessage = "";
        if (infoContentField.getText().isEmpty() || infoContentField.getText()=="")
            popupMessage+="Input the information content";

        if(!popupMessage.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, popupMessage, ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setSelectedInfoCategory(InfoCategory selectedInfoCategory) {
        this.selectedInfoCategory = selectedInfoCategory;
        infoCategoryNameLabel.setText(selectedInfoCategory.getName());
        definitionLabel.setText(selectedInfoCategory.getDefinition());
        GeneralInfo generalInfo=null;
        try {
            generalInfo = model.getGeneralInfoCitizen(GlobalCitizen.getSelectedCitizen(), selectedInfoCategory);
        } catch (GeneralInfoException e) {
            DisplayMessage.displayError(e);
        }
        assert generalInfo != null;
        if(generalInfo!=null)
            infoContentField.setText(generalInfo.getContent());
        Tooltip exampleTooltip = new Tooltip(selectedInfoCategory.getExample());
        Tooltip.install(infoContentField,exampleTooltip);
    }

    Thread saveInformationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Citizen currentCitizen = GlobalCitizen.getSelectedCitizen();
            String infoContent = infoContentField.getText();
            try {
                model.saveInformation(currentCitizen,selectedInfoCategory,infoContent);
            } catch (GeneralInfoException e) {
                e.printStackTrace();
            }
        }
    });
}
