package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import gui.Model.CitizenModel;
import gui.Model.TeacherModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    private TeacherModel teacherModel;
    private CitizenModel citizenModel;

    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesLastName;
    @FXML
    private TableView<Citizen> tableViewCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenLastName;
    @FXML
    private TableView<Student> tableViewStudent;
    @FXML
    private TableColumn<Student, String> tableColumnStudentFirstName;
    @FXML
    private TableColumn<Student, String> tableColumnStudentLastName;
    @FXML
    private TableView<Citizen> tableViewFictiveCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnFictiveCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenLastName;
    @FXML
    private TableView<Citizen> tableViewAssignedCit;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnAssignedID;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedLastName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.teacherModel = new TeacherModel();
            this.tableViewTemplates.setItems(teacherModel.getTemplatesObs());
            this.citizenModel = new CitizenModel();
            this.tableViewCitizen.setItems(citizenModel.getObsListCitizens());
            initTables();
        } catch (IOException | CitizenException e) {
            DisplayMessage.displayError(e);
        }
    }

    private void initTables() {
        //Templates
        this.tableColumnTemplatesFirstName.setCellValueFactory(new PropertyValueFactory<>("fNameProperty"));
        this.tableColumnTemplatesLastName.setCellValueFactory(new PropertyValueFactory<>("lNameProperty"));

        //Citizens on template page
        this.tableColumnCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Fictive Citizens on student page
        this.tableColumnFictiveCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnFictiveCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnFictiveCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Students
        this.tableColumnStudentFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnStudentLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //Assigned Fictive Citizens
        this.tableColumnAssignedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnAssignedFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnAssignedLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));
    }

    public void handleCreateTemplate(ActionEvent actionEvent) {
    }

    public void handleEditTemplate(ActionEvent actionEvent) {
    }

    public void handleDeleteTemplate(ActionEvent actionEvent) {
    }

    public void handleDuplicateTemplate(ActionEvent actionEvent) {
    }

    public void handleEditCitizen(ActionEvent actionEvent) {
    }

    public void handleDeleteCitizen(ActionEvent actionEvent) {
    }

    public void handleDuplicateCitizen(ActionEvent actionEvent) {
    }

    public void handleRemoveCitClick(ActionEvent actionEvent) {
    }

    public void handleCreateCitFromTemp(ActionEvent actionEvent) {
    }

    public void handleCreateTempFromCit(ActionEvent actionEvent) {
    }

    public void handleCreateStudent(ActionEvent actionEvent) {
    }

    public void handleEditStudent(ActionEvent actionEvent) {
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
    }

    public void handleAssignClick(ActionEvent actionEvent) {
    }

}
