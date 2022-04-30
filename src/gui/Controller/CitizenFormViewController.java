package gui.Controller;

import be.Citizen;
import bll.exceptions.CitizenException;
import gui.Model.CitizenModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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

    @FXML
    private CheckBox templateCheckBox;

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
    private void handleSave(ActionEvent actionEvent) {
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
                return;

        Citizen newCitizen = new Citizen(-1,fName,lName,cprNumber);
        newCitizen.setAddress(address);
        newCitizen.setBirthDate(birthDate);
        newCitizen.setCprNumber(cprNumber);
        newCitizen.setPhoneNumber(phoneNumber);
        newCitizen.setTemplate(true);

        try {
            citizenModel.createNewCitizen(newCitizen);
        } catch (CitizenException e) {
            e.printStackTrace();
        }


    }
}
