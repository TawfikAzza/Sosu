package gui.Controller;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GInfoModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralInfoFormController implements Initializable {

    @FXML
    private Label infoCategoryNameLabel;

    @FXML
    private Label definitionLabel;

    @FXML
    private TextArea infoContentField;

    private InfoCategory selectedInfoCategory;

    GInfoModel model;

    public GeneralInfoFormController() {
        try {
            model = new GInfoModel();
        } catch (GeneralInfoException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (checkFields()){
            closeWindow(event);
            saveInformation();
        }
    }

    @FXML
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
            generalInfo = model.getGeneralInfoCitizen(GlobalVariables.getSelectedCitizen(), selectedInfoCategory);
        } catch (GeneralInfoException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
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
            Citizen currentCitizen = GlobalVariables.getSelectedCitizen();
            String infoContent = infoContentField.getText();
            try {
                model.saveInformation(currentCitizen,selectedInfoCategory,infoContent);
            } catch (GeneralInfoException e) {
                DisplayMessage.displayError(e);
                e.printStackTrace();
            }
        }
    });

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Button sourceButton = ((Button) actionEvent.getSource());
        ((Stage) sourceButton.getParent().getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox infoParentBox = ((HBox) infoContentField.getParent());
        infoContentField.prefHeightProperty().bind(infoParentBox.heightProperty());
        infoContentField.prefWidthProperty().bind(infoParentBox.widthProperty());
    }
}
