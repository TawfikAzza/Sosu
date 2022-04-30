package gui.Controller;

import be.Citizen;
import bll.exceptions.CitizenException;
import bll.util.GlobalCitizen;
import gui.Model.CitizenModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CitizenFormViewController implements Initializable {

    @FXML
    private TextField cprNumberField;

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


    private int schoolID;

    private CitizenModel citizenModel;

    public CitizenFormViewController() {
        try {
            citizenModel = new CitizenModel();
        } catch (CitizenException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidators();
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAdditionalInfo(ActionEvent actionEvent) throws IOException {

        //TODO retrieve just created citizen, to change its additional inf (functional abilities,health conditions and general info)
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
        saveCitizen();
    }

    private void setupValidators() {
        phoneField.setTextFormatter(intFormatter);
    }

    private boolean saveCitizen(){
        String fName = fNameField.getText();
        String lName = lNAmeField.getText();
        String address = addressField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String cprNumber = cprNumberField.getText();
        int phoneNumber = -1;

        if (!phoneField.getText().isEmpty())
            phoneNumber = Integer.parseInt(phoneField.getText());

        if (fName.isEmpty() || lName.isEmpty() || address.isEmpty()
                || birthDate.equals(null) || phoneNumber==-1 || cprNumber.isEmpty())
            return false;

        Citizen newCitizen = new Citizen(-1,fName,lName,cprNumber);

        newCitizen.setAddress(address);
        newCitizen.setBirthDate(birthDate);
        newCitizen.setCprNumber(cprNumber);
        newCitizen.setPhoneNumber(phoneNumber);
        newCitizen.setTemplate(true);

        try {
            Citizen createdCitizen = citizenModel.createNewCitizen(newCitizen);
            GlobalCitizen.setSelectedCitizen(createdCitizen);
        } catch (CitizenException e) {
            e.printStackTrace();
        }

        return true;
    }

    TextFormatter intFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });
}
