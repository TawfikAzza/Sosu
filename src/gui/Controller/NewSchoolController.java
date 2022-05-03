package gui.Controller;

import be.School;
import bll.exceptions.SchoolException;
import com.jfoenix.controls.JFXButton;
import gui.Model.SchoolModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewSchoolController implements Initializable {
    @FXML
    private GridPane mainGridPane;
    @FXML
    private JFXButton cnfrmBtn;
    @FXML
    private TextField schoolName;
    private SchoolModel schoolModel;

    AdminViewController adminViewController;
    private ObservableList<School> allSchools= FXCollections.observableArrayList();

    public void handleCancelCreation(ActionEvent actionEvent) {
        boolean cancel=true;
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
            }
        else cancel=false;
        }
        if (cancel){
        Stage stage = (Stage) cnfrmBtn.getScene().getWindow();
        stage.close();
        }
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
        mainGridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    handleConfirmNewSchool(new ActionEvent());
                }else if(event.getCode().equals(KeyCode.ESCAPE)){
                    handleCancelCreation(new ActionEvent());}
            }
        });
    }

    public void setController(AdminViewController adminViewController) {
        this.adminViewController=adminViewController;
    }

    public void setListSchool(ObservableList<School> items) {
        allSchools=items;
    }
}
