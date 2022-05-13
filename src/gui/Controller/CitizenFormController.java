package gui.Controller;

import be.Citizen;
import be.School;
import bll.exceptions.CitizenException;
import bll.exceptions.SchoolException;
import bll.util.GlobalVariables;
import com.jfoenix.controls.JFXComboBox;
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
    private JFXComboBox<School> schoolChoiceBox;
    @FXML
    private VBox schoolBox2;
    @FXML
    private VBox schoolBox1;
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
    private SchoolModel schoolModel;

    private Citizen citizenToEdit;
    private boolean citizenCreation=true;
    private int currentSchoolId;

    public CitizenFormController() {
        citizenToEdit = null;
        try {
            citizenModel = CitizenModel.getInstance();
            schoolModel = new SchoolModel();
        } catch (CitizenException | SchoolException e) {
            DisplayMessage.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidators();
        bindSizes();
        enableDisableSchoolChoice();//disabled but could be nice if the admin wanted to create templates for different schools
        loadSchools();
    }

    public Citizen getCitizenToEdit() {
        return citizenToEdit;
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
    }

    private void loadSchools() {
        if (adminModeDisabled())
            return;
        try {
            schoolChoiceBox.setItems(schoolModel.getAllSchools());
        }
        catch (SchoolException e) {
            DisplayMessage.displayError(e);
        }

    }

    private boolean adminModeDisabled() {
        if (schoolBox1.isDisabled())
            return false;
        return true;
    }


    public void enableDisableSchoolChoice() {
        if (schoolBox1.isDisabled()){

            schoolBox1.setDisable(false);
            schoolBox1.setOpacity(100);

            schoolBox2.setDisable(false);
            schoolBox2.setOpacity(100);
        }
        else{

            schoolBox1.setDisable(true);
            schoolBox1.setOpacity(0);

            schoolBox2.setDisable(true);
            schoolBox2.setOpacity(0);
        }
    }

    private void bindSizes() {
        VBox choiceBoxParent = ((VBox) schoolChoiceBox.getParent());
        schoolChoiceBox.prefWidthProperty().bind(choiceBoxParent.widthProperty());
        VBox datePickerParent = ((VBox) birthDatePicker.getParent());
        birthDatePicker.prefWidthProperty().bind(datePickerParent.widthProperty());
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
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
        LocalDate birthDate = birthDatePicker.getValue();
        int phoneNumber = -1;

        if (!phoneField.getText().isEmpty())
            phoneNumber = Integer.parseInt(phoneField.getText());

        if (!inputIsValid(fName,lName,address,birthDate,phoneNumber))
            return false;

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
            createTemplate(newCitizen);
        }

        return true;
    }

    private boolean inputIsValid(String fName, String lName, String address, LocalDate birthDate, int phoneNumber) {
        String popupMessage = "";
        if (fName.isBlank())
            popupMessage+="- Enter a first name \n";
        if (lName.isBlank())
            popupMessage+="- Enter a last name \n";
        if (address.isBlank())
            popupMessage+="- Enter an address\n";
        if (birthDate==null)
            popupMessage+="- Select a birthdate\n";
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
                   citizenModel.refreshTemplates();
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
                    citizenModel.refreshTemplates();
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

    public void setCurrentSchoolId() {
        currentSchoolId= GlobalVariables.getCurrentSchool().getId();
    }
}
