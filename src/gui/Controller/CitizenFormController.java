package gui.Controller;

import be.Citizen;
import be.School;
import bll.exceptions.CitizenException;
import bll.exceptions.SchoolException;
import bll.util.GlobalVariables;
import com.jfoenix.controls.JFXComboBox;
import gui.Model.CitizenModel;
import gui.Model.SchoolModel;
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
import java.sql.SQLException;
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
    private SchoolModel schoolModel;

    private Citizen citizenToEdit;

    public CitizenFormController() {
        citizenToEdit = null;
        try {
            citizenModel = new CitizenModel();
            schoolModel = new SchoolModel();
        } catch (CitizenException | SchoolException e) {
            e.printStackTrace();
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
    }

    private void fillFields(Citizen citizenToEdit) {
        fNameField.setText(citizenToEdit.getFName());
        lNAmeField.setText(citizenToEdit.getLName());
        addressField.setText(citizenToEdit.getAddress());
        birthDatePicker.setValue(citizenToEdit.getBirthDate());
        cprNumberField.setText(citizenToEdit.getCprNumber());
        phoneField.setText(String.valueOf(citizenToEdit.getPhoneNumber()));
    }

    private void loadSchools() {
        if (adminModeDisabled())
            return;
        try {
            schoolChoiceBox.setItems(schoolModel.getAllSchools());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SchoolException e) {
            e.printStackTrace();
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

        if (citizenToEdit==null)
            createCitizen(newCitizen);
        else
            editCitizen(citizenToEdit,newCitizen);

        return true;
    }

    private void createCitizen(Citizen newCitizen) {
        Thread createCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Citizen citizen = null;
                try {
                    citizen = citizenModel.createNewCitizen(newCitizen);
                } catch (CitizenException e) {
                    e.printStackTrace();
                }
                GlobalVariables.setSelectedCitizen(citizen);
            }
        });
        createCitizenThread.start();
    }

    private void editCitizen(Citizen citizenToEdit,Citizen newCitizen){
        Thread createCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Citizen editedCitizen = null;
                try {
                    editedCitizen = citizenModel.editCitizen(citizenToEdit, newCitizen);
                } catch (CitizenException e) {
                    e.printStackTrace();
                }
                GlobalVariables.setSelectedCitizen(editedCitizen);
            }
        });
        createCitizenThread.start();

    }

    TextFormatter intFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });
}
