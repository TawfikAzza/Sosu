package gui.Controller;

import be.Case;
import be.Citizen;
import bll.exceptions.CaseException;
import bll.exceptions.CitizenException;
import bll.util.CheckInput;
import bll.util.DateUtil;
import bll.util.GlobalVariables;
import gui.Model.CaseModel;
import gui.Model.CitizenModel;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CitizenFormController implements Initializable {

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNAmeField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField birthDatePicker;
    @FXML
    private TextField phoneField;

    private CitizenModel citizenModel;
    private CaseModel caseModel;

    private Citizen citizenToEdit;
    private boolean citizenCreation=true;
    private int currentSchoolId;
    private int caseID = -1;

    public CitizenFormController() {
        citizenToEdit = null;
        try {
            this.citizenModel = CitizenModel.getInstance();
            this.caseModel = new CaseModel();
        } catch (CitizenException | IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidators();
        bindSizes();
        toolTips();
    }

    private void toolTips() {
        Tooltip dateToolTip = new Tooltip("dd/MM/yyyy");
        Tooltip.install(birthDatePicker,dateToolTip);
        dateToolTip.setShowDelay(Duration.millis(300));
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
        birthDatePicker.setText(DateUtil.formatDateGui(citizenToEdit.getBirthDate()));
        phoneField.setText(String.valueOf(citizenToEdit.getPhoneNumber()));
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

    @FXML
    private void handleAddEditCaseClick(ActionEvent actionEvent) {
        if(citizenCreation)
        {
            try {
                CaseCreationController controller = new CaseCreationController(this, false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CaseCreationView.fxml"));
                loader.setController(controller);
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage newWindow = new Stage();
                newWindow.setScene(scene);
                newWindow.show();
            } catch (IOException e) {
                DisplayMessage.displayError(e);
            }
        }
        if(!citizenCreation)
        {
            try {
                CaseCreationController controller = new CaseCreationController(this, true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CaseCreationView.fxml"));
                loader.setController(controller);
                Parent root = loader.load();
                controller.setCitCase(caseModel.getCitizenCase(citizenToEdit));

                Scene scene = new Scene(root);
                Stage newWindow = new Stage();
                newWindow.setScene(scene);
                newWindow.show();
            } catch (CaseException | IOException e) {
                DisplayMessage.displayError(e);
            }
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
        birthDatePicker.setTextFormatter(dateFormatter);
    }


    private boolean saveCitizen(){
        //Get values from fields
        String fName = fNameField.getText();
        String lName = lNAmeField.getText();
        String address = addressField.getText();
        String dateString  = birthDatePicker.getText();

        //initialize phone number to -1 cause the user can't input the "-" symbol, so we know there is no input
        int phoneNumber = -1;

        //try to parse the phoneNumber input (should throw no exceptions since the textfield has a textformatter applied )
        if (!phoneField.getText().isEmpty())
            phoneNumber = Integer.parseInt(phoneField.getText());

        //Validate input (logical operation so maybe should have been moved somewhere else)
        if (!inputIsValid(fName,lName,address,dateString,phoneNumber))
            return false;

        //Parse the birthdate after it's been validated as good otherwise it could throw and exception
        LocalDate birthDate = DateUtil.parseDate_GUI(dateString);

        // if we're not creating a new citizen then set the new values for the citizen we're editing
        if (!citizenCreation)
        {
            citizenToEdit.setFName(fName);
            citizenToEdit.setLName(lName);
            citizenToEdit.setAddress(address);
            citizenToEdit.setBirthDate(birthDate);
            citizenToEdit.setPhoneNumber(phoneNumber);
            citizenToEdit.setSchoolID(currentSchoolId);
            //Call the editing of the data in the db
            editTemplate(citizenToEdit);
        }
        // Otherwise, we're creating a new citizen
        else {
            Citizen newCitizen = new Citizen(-1,fName,lName,address,phoneNumber,birthDate,true,currentSchoolId);
            //If before saving the new citizen you have created a case we add the case id to the citizen
            if(caseID!=-1) {
                newCitizen.setCaseID(caseID);
            }
            //by default if before saving the citizen you've not created a case we create a default case and add it to the citizen
            if(caseID==-1)
            {
                Case newCase = new Case("Default Case", "No Content");
                try {
                    int caseID = caseModel.addCase(newCase);
                    newCitizen.setCaseID(caseID);
                } catch (CaseException e) {
                    e.printStackTrace();
                }
            }
            //We try to create a new citizen.
            if (createTemplate(newCitizen)==null)
                return false;
        }
        //If all went according to plan we return true and the citizen form would be closed
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

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }
}
