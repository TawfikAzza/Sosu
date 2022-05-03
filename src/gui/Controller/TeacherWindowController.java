package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import gui.Model.StudentModel;
import gui.Model.TeacherModel;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherWindowController implements Initializable {

    private final TeacherModel model;
    private final UserModel userModel;
    private final StudentModel studentModel;

    @FXML
    private TableView<Citizen> tableViewAssignments;
    @FXML
    private TableColumn<Citizen, String> fNameAssignmentsColumn;
    @FXML
    private TableColumn<Citizen, String> lNameAssignmentsColumn;
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
        this.studentModel = new StudentModel();
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


    private void initTables() {
        //citizens
        this.fNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //students
        this.fNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //assignments
        this.fNameAssignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameAssignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
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
    private void handleCreateCitizen(ActionEvent actionEvent) throws IOException {
        openCitizenForm(false,null);
    }


    @FXML
    private void handleEditCitizen(ActionEvent actionEvent) throws IOException {
        Citizen citizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (citizen == null)
            return;
        openCitizenForm(true,citizen);
    }

    private void openCitizenForm(boolean isEditing, Citizen citizen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/CitizenFormView.fxml"));
        Parent root = loader.load();

        CitizenFormController formController = loader.getController();
        if (isEditing)
            formController.setCitizenToEdit(citizen);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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

    public void handleLoadAssignments(ActionEvent actionEvent) {
        try {
            Student selectedStudent = tableViewStudents.getSelectionModel().getSelectedItem();
            ObservableList<Citizen> citizens = studentModel.getCitizensOfStudent(selectedStudent);
            tableViewAssignments.setItems(citizens);
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
        }
    }
}
