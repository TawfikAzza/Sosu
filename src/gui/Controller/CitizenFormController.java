package gui.Controller;

import be.Case;
import be.Citizen;
import be.School;
import bll.exceptions.CaseException;
import bll.exceptions.CitizenException;
import bll.exceptions.SchoolException;
import bll.util.CheckInput;
import bll.util.DateUtil;
import bll.util.GlobalVariables;
import com.jfoenix.controls.JFXComboBox;
import gui.Model.CaseModel;
import gui.Model.CitizenModel;
import gui.Model.SchoolModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CitizenFormController implements Initializable {

    @FXML
    private Label labelCase;
    @FXML
    private ChoiceBox<Case> choiceBoxCases;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNAmeField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField phoneField;

    private CitizenModel citizenModel;

    private Citizen citizenToEdit;
    private boolean citizenCreation=true;
    private int currentSchoolId;
    private CaseModel caseModel;

    public CitizenFormController() {
        citizenToEdit = null;
        try {
            citizenModel = CitizenModel.getInstance();
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidators();
        bindSizes();
        try {
            caseModel = new CaseModel();
            choiceBoxCases.setItems(caseModel.getObsCases());
        } catch (IOException | CaseException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public void setCitizenToEdit(Citizen citizenToEdit) {
        this.citizenToEdit = citizenToEdit;
        fillFields(citizenToEdit);
        citizenCreation=false;
    }

    private void fillFields(Citizen citizenToEdit) {
        fNameField.setText(citizenToEdit.getFName());
        lNAmeField.setText(citizenToEdit.getLName());
        addressField.setText(citizenToEdit.getAddress());
        birthDatePicker.setValue(citizenToEdit.getBirthDate());
        phoneField.setText(String.valueOf(citizenToEdit.getPhoneNumber()));
        choiceBoxCases.setVisible(false);
        labelCase.setVisible(false);
    }

    private void bindSizes() {
        VBox datePickerParent = ((VBox) birthDatePicker.getParent());
        birthDatePicker.prefWidthProperty().bind(datePickerParent.widthProperty());
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Button actionSource = ((Button) actionEvent.getSource());
        Scene currentScene = actionSource.getScene();
        Stage currentStage = ((Stage) currentScene.getWindow());
        currentStage.close();
    }

    @FXML
    private void handleAdditionalInfo(ActionEvent actionEvent) throws IOException {
        if (saveCitizen()) {
            closeThisWindow();
            openAdditionalInfoWindow();
        }
    }

    private void closeThisWindow() {
        Scene currentScene = fNameField.getScene();
        Stage currentStage = ((Stage) currentScene.getWindow());
        currentStage.close();
    }

    private void openAdditionalInfoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditAdditionalInfoView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setScene(scene);
        newWindow.show();
    }

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        if(saveCitizen())
            closeThisWindow();
    }

    private void setupValidators() {
        phoneField.setTextFormatter(intFormatter);
        datePickerMaxDate();
        birthDatePicker.getEditor().setTextFormatter(dateFormatter);
    }

    private void datePickerMaxDate() {
        LocalDate maxDate = LocalDate.now();
        birthDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate));
                    }});
    }

    private boolean saveCitizen(){
        String fName = fNameField.getText();
        String lName = lNAmeField.getText();
        String address = addressField.getText();
        String dateString  = birthDatePicker.getEditor().getText();
        int phoneNumber = -1;
        int caseID = 2;

        if(choiceBoxCases.getSelectionModel().getSelectedItem()!=null) {
            caseID = choiceBoxCases.getSelectionModel().getSelectedItem().getId();
        }

        if (!phoneField.getText().isEmpty())
            phoneNumber = Integer.parseInt(phoneField.getText());

        if (!inputIsValid(fName,lName,address,dateString,phoneNumber))
            return false;

        LocalDate birthDate = DateUtil.parseDate_GUI(dateString);

        if (!citizenCreation)
        {
            citizenToEdit.setFName(fName);
            citizenToEdit.setLName(lName);
            citizenToEdit.setAddress(address);
            citizenToEdit.setBirthDate(birthDate);
            citizenToEdit.setPhoneNumber(phoneNumber);
            citizenToEdit.setSchoolID(currentSchoolId);
            editTemplate(citizenToEdit);
        }
        else {
            Citizen newCitizen = new Citizen(-1,fName,lName,address,phoneNumber,birthDate,true,currentSchoolId);
            newCitizen.setCaseID(caseID);
            createTemplate(newCitizen);
        }
        return true;
    }

    private boolean inputIsValid(String fName, String lName, String address, String birthDate, int phoneNumber) {
        String popupMessage = "";
        if (fName.isBlank())
            popupMessage+="- Enter a first name \n";
        if (lName.isBlank())
            popupMessage+="- Enter a last name \n";
        if (address.isBlank())
            popupMessage+="- Enter an address\n";
        if (!DateUtil.validDate(birthDate))
            popupMessage+="- Enter a valid date of the format dd/MM/yyyy\n";
        if (DateUtil.validDate(birthDate) && !CheckInput.isDateBeforeToday(birthDate))
            popupMessage+="- Enter a valid date before today\n";
        if (phoneNumber==-1)
            popupMessage+="- Enter a phone number\n";
        if (String.valueOf(phoneNumber).length()<8)
            popupMessage+="- Make sure the phone number is valid";

        if(!popupMessage.equals("")) {
            DisplayMessage.displayMessage(popupMessage);
            return false;
        }
        return true;
    }



    private Citizen createTemplate(Citizen newCitizen) {
        Citizen citizen = null;
        try {
           citizen= citizenModel.createNewCitizen(newCitizen);
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
        }
        GlobalVariables.setSelectedCitizen(citizen);
        return citizen;
    }

    private void editTemplate(Citizen citizenToEdit){
        Thread editTemplateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Citizen editedCitizen = null;
                try {
                    editedCitizen = citizenModel.editCitizen(citizenToEdit);
                } catch (CitizenException e) {
                    e.printStackTrace();
                    DisplayMessage.displayError(e);
                }
                GlobalVariables.setSelectedCitizen(editedCitizen);
            }
        });
        editTemplateThread.start();

    }

    TextFormatter intFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*") && change.getControlText().length()<8
                || change.isDeleted() || change.getText().isEmpty())
            return change;
        return null;
    });

    TextFormatter dateFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[/0-9]*"))
            return change;
        return null;
    });

    public void setCurrentSchoolId() {
        currentSchoolId= GlobalVariables.getCurrentSchool().getId();
    }
}
