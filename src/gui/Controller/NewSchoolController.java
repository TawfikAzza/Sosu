package gui.Controller;

import be.School;
import bll.exceptions.SchoolException;
import com.jfoenix.controls.JFXButton;
import gui.Model.SchoolModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewSchoolController implements Initializable {
    @FXML
    private JFXButton cnfrmBtn;
    @FXML
    private TextField schoolName;
    private SchoolModel schoolModel;

    AdminViewController adminViewController;
    private ObservableList<School> allSchools= FXCollections.observableArrayList();

    public void handleCancelCreation(ActionEvent actionEvent) {
        if (!(schoolName.getText().isEmpty()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Are you sure you want to close this window ?");
            alert.setContentText("All your infos will be lost in this case.");
            alert.showAndWait();
            if (alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) cnfrmBtn.getScene().getWindow();
                stage.close();
            }}
    }

    public void handleConfirmNewSchool(ActionEvent actionEvent)  {
        try {
            School newSchool = schoolModel.newSchool(schoolName.getText());
            allSchools.add(newSchool);
            adminViewController.refreshLViewSchools(allSchools);

            Stage stage = (Stage) cnfrmBtn.getScene().getWindow();
            stage.close();
        }catch (SchoolException schoolException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText(schoolException.getExceptionMessage());
            alert.setContentText(schoolException.getInstructions());
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            schoolModel =new SchoolModel();
        } catch (SchoolException e) {
            e.printStackTrace();
        }
    }

    public void setController(AdminViewController adminViewController) {
        this.adminViewController=adminViewController;
    }

    public void setListSchool(ObservableList<School> items) {
        allSchools=items;
    }
}
