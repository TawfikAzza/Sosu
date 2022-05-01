package gui.Controller;

import be.Citizen;
import be.Student;
import be.Teacher;
import gui.Model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    public TableColumn<Teacher,String> firstNameTeacher,lastNameTeacher,userNameTeacher,passWordTeacher,emailTeacher,schoolTeacher;
    public TableColumn<Teacher,Integer> phoneNumberTeacher;
    @FXML
    private TextField searchTeacherField,searchStudentField,searchCitizenField,searchStudentFieldSchool,searchSchoolField,searchCitizenSchoolField;
    @FXML
    private TableView<Teacher> teachersTableView;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private ListView<Citizen> citizensStudentRelatedListView;
    @FXML
    private TableView<Citizen> citizensTableView;

    private ObservableList<Teacher> allTeachersFilter;

    UserModel userModel;


    public void deleteTeacher(ActionEvent actionEvent) throws SQLException {
        Teacher selectedTeacher = teachersTableView.getSelectionModel().getSelectedItem();
        userModel.deleteTeacher(selectedTeacher);

    }

    public void addTeacher(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("New Teacher");
        stage.setScene(new Scene(root));
        stage.show();
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
        try {
            userModel=UserModel.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeTeachersTV();
        searchTeacherField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        allTeachersFilter= FXCollections.observableArrayList();
                        allTeachersFilter.addAll(userModel.getAllTeachers(searchTeacherField.getText()));
                        teachersTableView.setItems(allTeachersFilter);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void initializeTeachersTV(){
        firstNameTeacher.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTeacher.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userNameTeacher.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passWordTeacher.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        emailTeacher.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberTeacher.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        schoolTeacher.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
    }

    public void editTeacher(ActionEvent actionEvent) throws IOException {
        if (teachersTableView.getSelectionModel().getSelectedItem()!=null){
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.editTeacher(teachersTableView.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setTitle("Edit Teacher");
        stage.setScene(new Scene(root));
        stage.show();
    }
    }

    public void handleEditStudent(ActionEvent actionEvent) throws IOException {
        if (studentsTableView.getSelectionModel().getSelectedItem()!=null){
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.editStudent(studentsTableView.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setTitle("Edit Student");
        stage.setScene(new Scene(root));
        stage.show();
    }
    }
}
