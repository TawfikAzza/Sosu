package gui.Controller;

import be.Citizen;
import be.School;
import be.Student;
import be.Teacher;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import gui.Model.SchoolModel;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private ListView<String> citizensSchoolLV;
    @FXML
    private TextField searchTeacherFieldSchool;
    @FXML
    private ListView<String> allStudentsSchool;
    @FXML
    private ListView<String> allTeachersSchool;
    @FXML
    private ListView<School> allSchoolsLV;
    @FXML
    private TableColumn<Teacher,String> firstNameTeacher,lastNameTeacher,userNameTeacher,passWordTeacher,emailTeacher,schoolTeacher;
    @FXML
    private TableColumn<Teacher,Integer> phoneNumberTeacher;
    @FXML
    private TableColumn<Student,String> firstNameStudent,lastNameStudent,userNameStudent,passWordStudent,emailStudent,schoolStudent;
    @FXML
    private TableColumn<Student,Integer>phoneNumberStudent;

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

    private UserModel userModel;
    private SchoolModel schoolModel;

    private ObservableList<Teacher>allTeacherFiltered=FXCollections.observableArrayList();
    private ObservableList<Student>allStudentsFiltered=FXCollections.observableArrayList();

    private final List<String>allTeachers=new ArrayList<>();
    private final List<String>allStudents=new ArrayList<>();
    private final List<School>allSchools=new ArrayList<>();
    private final List<String>allCitizens=new ArrayList<>();





    public void deleteTeacher(ActionEvent actionEvent) throws SQLException {
        if (teachersTableView.getSelectionModel().getSelectedItem()!=null){
        Teacher selectedTeacher = teachersTableView.getSelectionModel().getSelectedItem();
        userModel.deleteTeacher(selectedTeacher);
        }

    }

    public void addTeacher(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.updateTView(allTeacherFiltered,searchTeacherField.getText());
        newEditUserController.setController(this);

        Stage stage = new Stage();
        stage.setTitle("New Teacher");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void deleteStudent(ActionEvent actionEvent) throws SQLException {
        if ( studentsTableView.getSelectionModel().getSelectedItem()!=null){
        Student selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        userModel.deleteStudent(selectedStudent);
        }
    }

    public void addStudent(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.newStudent();
        newEditUserController.updateTViewStudent(allStudentsFiltered,searchTeacherField.getText());
        newEditUserController.setController(this);

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void manageHealth(ActionEvent actionEvent) {
    }

    public void deleteCitizen(ActionEvent actionEvent) {
    }

    public void addCitizen(ActionEvent actionEvent) {
    }

    public void deleteSchool(ActionEvent actionEvent) {
    }

    public void addSchool(ActionEvent actionEvent) throws IOException {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewSchool.fxml"));
            root = loader.load();

            NewSchoolController newSchoolController = loader.getController();
            newSchoolController.setController(this);
            newSchoolController.setListSchool(allSchoolsLV.getItems());

            Stage stage = new Stage();
            stage.setTitle("Edit Teacher");
            stage.setScene(new Scene(root));
            stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userModel=UserModel.getInstance();
            schoolModel = new SchoolModel();

        } catch (IOException | SchoolException e) {
            e.printStackTrace();
        }

        initializeTeachersTV();
        initializeStudentsTV();
        try {
            allSchoolsLV.setItems(schoolModel.getAllSchools());
            allSchools.addAll(allSchoolsLV.getItems());
        } catch (SQLException | SchoolException e) {
            e.printStackTrace();
        }

        allSchoolsLV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    allStudentsSchool.setItems(schoolModel.getAllStudents(allSchoolsLV.getSelectionModel().getSelectedItem()));
                    allStudents.addAll(allStudentsSchool.getItems());

                    allTeachersSchool.setItems(schoolModel.getAllTeachers(allSchoolsLV.getSelectionModel().getSelectedItem()));
                    allTeachers.addAll(allTeachersSchool.getItems());

                    citizensSchoolLV.setItems(schoolModel.getAllCitizens(allSchoolsLV.getSelectionModel().getSelectedItem()));
                    allCitizens.addAll(citizensSchoolLV.getItems());

                } catch (SQLException | UserException e) {
                    e.printStackTrace();
                }
            }
        });

        searchTeacherField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        allTeacherFiltered.setAll(userModel.getAllTeachers(searchTeacherField.getText()));
                        teachersTableView.setItems(allTeacherFiltered);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        searchStudentField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        allStudentsFiltered.setAll(userModel.getAllStudents(searchStudentField.getText()));
                        studentsTableView.setItems(allStudentsFiltered);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        SearchFilterSchool(searchStudentFieldSchool, allStudents, allStudentsSchool);

        SearchFilterSchool(searchTeacherFieldSchool, allTeachers, allTeachersSchool);

        searchSchoolField.setOnKeyReleased(event->{
            ObservableList<School>allSchoolsFiltered=FXCollections.observableArrayList();
            for (School school :allSchools){
                if (school.getName().toLowerCase(Locale.ROOT).contains(searchSchoolField.getText().toLowerCase(Locale.ROOT))){
                    allSchoolsFiltered.add(school);
                }
                allSchoolsLV.setItems(allSchoolsFiltered);
            }
        });

        searchCitizenSchoolField.setOnKeyReleased(event -> {
            ObservableList<String>allCitizensFiltered=FXCollections.observableArrayList();
            for (String str :allCitizens){
                if (str.toLowerCase(Locale.ROOT).contains(searchCitizenSchoolField.getText().toLowerCase(Locale.ROOT))){
                    allCitizensFiltered.add(str);
                }
                citizensSchoolLV.setItems(allCitizensFiltered);
            }
        });

    }

    private void SearchFilterSchool(TextField searchTeacherFieldSchool, List<String> allTeachers, ListView<String> allTeachersSchool) {
        searchTeacherFieldSchool.setOnKeyReleased(event -> {
            ObservableList<String> allTeachersFiltered= FXCollections.observableArrayList();
            for (String string : allTeachers){
                if (string.toLowerCase(Locale.ROOT).contains(searchTeacherFieldSchool.getText().toLowerCase(Locale.ROOT)))
                    allTeachersFiltered.add(string);
            }
            allTeachersSchool.setItems(allTeachersFiltered);
        });
    }

    private void initializeStudentsTV() {
        firstNameStudent.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameStudent.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userNameStudent.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passWordStudent.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        emailStudent.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberStudent.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        schoolStudent.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
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
        newEditUserController.updateTView(allTeacherFiltered,searchTeacherField.getText());
        newEditUserController.setController(this);

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
        newEditUserController.updateTViewStudent(allStudentsFiltered,searchStudentField.getText());
        newEditUserController.setController(this);

        Stage stage = new Stage();
        stage.setTitle("Edit Student");
        stage.setScene(new Scene(root));
        stage.show();
    }
    }
    public void refreshTView(ObservableList<Teacher>allTeacherFiltered){
        teachersTableView.setItems(allTeacherFiltered);
    }

    public void refreshTViewStudents(ObservableList<Student>allStudentsFiltered){
        studentsTableView.setItems(allStudentsFiltered);
    }

    public void refreshLViewSchools(ObservableList<School>allSchools){
        allSchoolsLV.setItems(allSchools);
    }

}
