package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.UserException;
import gui.Model.TeacherModel;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherWindowController implements Initializable {

    private final TeacherModel model;
    private final UserModel userModel;
    private boolean isAdmin;

    @FXML
    private TableView<Student> tableViewStudents;
    @FXML
    private TableColumn<Student, String> fNameStudentsColumn;
    @FXML
    private TableColumn<Student, String> lNameStudentsColumn;
    @FXML
    private Button newStudentBtn,deleteStudentBtn,editStudentBtn;
    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> fNameTemplateTableColumn;
    @FXML
    private TableColumn<Citizen, String> lNameTemplateTableColumn;

    public TeacherWindowController() throws IOException {
        this.model = new TeacherModel();
        this.userModel = new UserModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<Citizen> cits = model.getTemplates();
            ObservableList<Student> studs = userModel.getStudents();
            this.tableViewTemplates.setItems(cits);
            this.tableViewStudents.setItems(studs);

            this.initTables();
        } catch (CitizenException | UserException e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private void initTables() {
        //citizens
        this.fNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //students
        this.fNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    @FXML
    private void handleActionDuplicate(ActionEvent actionEvent) {
        Citizen template = tableViewTemplates.getSelectionModel().getSelectedItem();
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        ArrayList<Student> students = new ArrayList<>();

        students.add(student);

        try {
            model.copyCitizenToDB(template, students);
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void setAdminView() {
        newStudentBtn.setDisable(true);
        editStudentBtn.setDisable(true);
        deleteStudentBtn.setDisable(true);
    }

    @FXML
    private void handleCreateCitizen(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEditCitizen(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDeleteCitizen(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCreateStudent(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEditStudent(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDeleteStudent(ActionEvent actionEvent) {
    }
}
