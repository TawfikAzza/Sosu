package gui.Controller;

import be.Citizen;
import be.School;
import bll.SchoolManager;
import bll.exceptions.SchoolException;

import bll.exceptions.UserException;
import gui.Model.SchoolModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewEditSchoolController {
    private SchoolModel schoolModel;
    private School school;

    public NewEditSchoolController() throws SchoolException, IOException, UserException {
        schoolModel= SchoolModel.getInstance();
    }

    @FXML
    private TextField schoolName, schoolAddress, pCode, regionSchool, schoolNumber;
    private boolean onAdd=true;

    public void setEdit(School selectedItem) {
        onAdd=false;
        school=selectedItem;
        schoolName.setText(selectedItem.getName());
    }


    public void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) schoolName.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmBtn(ActionEvent actionEvent) throws SchoolException, SQLException {
        if (onAdd)
            try {
                schoolModel.newSchool(schoolName.getText());
                Stage stage;
                stage = (Stage) schoolName.getScene().getWindow();
                stage.close();
            }catch (SchoolException schoolException){
                DisplayMessage.displayError(schoolException);
                DisplayMessage.displayMessage(schoolException.getExceptionMessage());
            }
        else {
            school.setName(schoolName.getText());
            schoolModel.editSchool(school,schoolName.getText());
            Stage stage;
            stage = (Stage) schoolName.getScene().getWindow();
            stage.close();
        }

    }
}
