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
import javafx.scene.control.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CitizenFormViewController implements Initializable {

    @FXML
    private VBox schoolBox2;
    @FXML
    private VBox schoolBox1;
    @FXML
    private ChoiceBox schoolChoiceBox;
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
        bindSizes();
        enableDisableSchoolChoice();


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

        Thread createCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Citizen createdCitizen = null;
                try {
                    createdCitizen = citizenModel.createNewCitizen(newCitizen);
                } catch (CitizenException e) {
                    e.printStackTrace();
                }
                GlobalCitizen.setSelectedCitizen(createdCitizen);
            }
        });
        createCitizenThread.start();

        return true;
    }

    TextFormatter intFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });
}
