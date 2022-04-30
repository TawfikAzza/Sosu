package gui.Controller;

import be.Citizen;
import be.Student;
import be.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private TextField searchTeacherField;
    @FXML
    private TableView<Teacher> teachersTableView;
    @FXML
    private TextField searchStudentField;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private ListView<Citizen> citizensStudentRelatedListView;
    @FXML
    private TextField searchCitizenField;
    @FXML
    private TableView<Citizen> citizensTableView;
    @FXML
    private TextField searchStudentFieldSchool;
    @FXML
    private TextField searchSchoolField;
    @FXML
    private TextField searchCitizenSchoolField;

    public void deleteTeacher(ActionEvent actionEvent) {
    }

    public void addTeacher(ActionEvent actionEvent) {
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void deleteStudent(ActionEvent actionEvent) {
    }

    public void addStudent(ActionEvent actionEvent) {
    }

    public void manageHealth(ActionEvent actionEvent) {
    }

    public void deleteCitizen(ActionEvent actionEvent) {
    }

    public void addCitizen(ActionEvent actionEvent) {
    }

    public void deleteSchool(ActionEvent actionEvent) {
    }

    public void addSchool(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
