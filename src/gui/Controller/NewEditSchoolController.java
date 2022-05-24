package gui.Controller;

import be.Citizen;
import be.School;
import bll.SchoolManager;
import bll.exceptions.SchoolException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewEditSchoolController {
    private SchoolManager schoolManager;
    private School school;

    public NewEditSchoolController() throws SchoolException {
        schoolManager= new SchoolManager();
    }

    @FXML
    private TextField schoolName, schoolAddress, pCode, regionSchool, schoolNumber;
    private boolean onAdd;

    public void setEdit(boolean b, School selectedItem) {
        onAdd=false;
        schoolName.setText(selectedItem.getName());
    }


    public void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) schoolName.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmBtn(ActionEvent actionEvent) throws SchoolException, SQLException {
        if (onAdd)
            schoolManager.newSchool(schoolName.getText());
        else schoolManager.editSchool(school);
    }
}
