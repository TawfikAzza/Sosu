package gui.Controller;

import be.*;
import bll.exceptions.CitizenException;
import bll.exceptions.SchoolException;
import bll.exceptions.StudentException;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private AnchorPane teacherPane;
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

    private UserModel userModel;
    private SchoolModel schoolModel;

    private School newSchool;
    private School selectedSchool;

    private Teacher selectedTeacher;
    private Student selectedStudent;

    private ObservableList<Teacher>allTeacherFiltered=FXCollections.observableArrayList();
    private ObservableList<Student>allStudentsFiltered=FXCollections.observableArrayList();

    private final List<String>allTeachers=new ArrayList<>();
    private final List<String>allStudents=new ArrayList<>();
    private final List<School>allSchools=new ArrayList<>();
    private final List<String>allCitizens=new ArrayList<>();

    private Integer test = 1;

    private UserException ue = new UserException();


    public void deleteTeacher(ActionEvent actionEvent) throws SQLException {
        if (teachersTableView.getSelectionModel().getSelectedItem()!=null){
        Teacher selectedTeacher = teachersTableView.getSelectionModel().getSelectedItem();
        userModel.deleteTeacher(selectedTeacher);
        allTeacherFiltered.remove(selectedTeacher);
        allTeachersSchool.getItems().remove(selectedTeacher.getFirstName()+" "+selectedTeacher.getLastName());
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
        allStudentsFiltered.remove(selectedStudent);
        allStudentsSchool.getItems().remove(selectedStudent.getFirstName()+" "+selectedStudent.getLastName());

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

                } catch (SQLException | UserException ignored) {
                }
            }
        });

        studentsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    citizensStudentRelatedListView.setItems(userModel.getCitizensOfStudent(studentsTableView.getSelectionModel().getSelectedItem()));
                } catch (StudentException | CitizenException ignored) {
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

        FXMLLoader loaderTeacher = new FXMLLoader();
        loaderTeacher.setLocation(getClass().getResource("/gui/View/TeacherWindow.fxml"));
        try {
            GridPane teacherDisplay = loaderTeacher.load();
            teacherPane.getChildren().add(teacherDisplay);
            teacherDisplay.prefHeightProperty().bind(teacherPane.heightProperty());
            teacherDisplay.prefWidthProperty().bind(teacherPane.widthProperty());
            TeacherWindowController teacherWindowController = loaderTeacher.getController();
            teacherWindowController.setAdminView();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        studentsTableView.setEditable(true);
        firstNameStudent.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserFN(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedStudent.getFirstName();
                }
                return string;
            }
        }));
        firstNameStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student  student = event.getRowValue();
                if (test>0){
                    student.setFirstName(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });

        lastNameStudent.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserLN(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedStudent.getLastName();
                }
                return string;
            }
        }));
        lastNameStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student  student = event.getRowValue();
                if (test>0){
                    student.setLastName(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });

        userNameStudent.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserUName(string,userModel.userNameTaken(string));
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedStudent.getUserName();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return string;
            }
        }));
        userNameStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student  student = event.getRowValue();
                if (test>0){
                    student.setUserName(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
        passWordStudent.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        passWordStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserPassword(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedStudent.getPassWord();
                }
                return string;
            }
        }));
        passWordStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student  student = event.getRowValue();
                if (test>0){
                    student.setPassWord(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
        emailStudent.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkEmail(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedStudent.getEmail();
                }
                return string;
            }
        }));
        emailStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student  student = event.getRowValue();
                if (test>0){
                    student.setEmail(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
        phoneNumberStudent.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    ue.checkPhoneNumber(string);
                } catch (UserException nfe) {
                    OnSchoolEditException(nfe.getExceptionMessage(), nfe.getInstructions());
                    return selectedTeacher.getPhoneNumber();

                }
                return test;
            }
        }));
        phoneNumberStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Integer> event) {
                Student student = event.getRowValue();
                if (test >= 0) {
                    student.setPhoneNumber(event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch ( UserException | SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    test = 1;
                }
            }
        });
        schoolStudent.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
        schoolStudent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    for (School school : allSchools)
                        if (school.getName().toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT))) {
                            newSchool=school;
                            return school.getName();
                        }
                    SchoolException schoolException = new SchoolException("School not found",new Exception());
                    schoolException.setInstructions("Please find an existing school");
                    throw schoolException;

                } catch (SchoolException e) {
                    OnSchoolEditException(e.getExceptionMessage(), e.getInstructions());
                    return selectedTeacher.getSchoolName();
                }
            }
        }));
        schoolStudent.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student student = event.getRowValue();
                if (test>0){
                    try {
                        userModel.editStudent(newSchool,student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
    }

    private void initializeTeachersTV(){

        teachersTableView.setEditable(true);
        firstNameTeacher.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserFN(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedTeacher.getFirstName();
                }
                return string;
            }
        }));
        firstNameTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0){
                teacher.setFirstName(event.getNewValue());
                    try {
                    userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                } catch (UserException e) {
                    e.printStackTrace();
                }
                }
                test=1;
            }
        });


        lastNameTeacher.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserLN(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedTeacher.getLastName();
                }
                return string;
            }
        }));
        lastNameTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0){
                    teacher.setLastName(event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }}
                test=1;
            }
        });

        userNameTeacher.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserUName(string,userModel.userNameTaken(string));
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedTeacher.getUserName();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return string;
            }
        }));
        userNameTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0)
                {
                    teacher.setUserName(event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
        passWordTeacher.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        passWordTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserPassword(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedTeacher.getUserName();
                }
                return string;
            }
        }));
        passWordTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0)
                {
                    teacher.setPassWord(event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
        emailTeacher.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkEmail(string);
                } catch (UserException e) {
                    ExceptionOnEdit(e);
                    return selectedTeacher.getEmail();
                }
                return string;
            }
        }));
        passWordTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0)
                {
                    teacher.setEmail(event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });

        phoneNumberTeacher.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    ue.checkPhoneNumber(string);
                } catch (UserException nfe) {
                    OnSchoolEditException(nfe.getExceptionMessage(), nfe.getInstructions());
                    return selectedTeacher.getPhoneNumber();

                }
                return test;
            }
        }));
        phoneNumberTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, Integer> event) {
                Teacher teacher = event.getRowValue();
                if (test >= 0) {
                    teacher.setPhoneNumber(event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch ( UserException e) {
                        e.printStackTrace();
                    }
                } else {
                    test = 1;
                }
            }
        });
        schoolTeacher.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
        schoolTeacher.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    for (School school : allSchools)
                        if (school.getName().toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT))) {
                            newSchool=school;
                            return school.getName();
                        }
                    SchoolException schoolException = new SchoolException("School not found",new Exception());
                    schoolException.setInstructions("Please find an existing school");
                    throw schoolException;

                } catch (SchoolException e) {
                    OnSchoolEditException(e.getExceptionMessage(), e.getInstructions());
                    return selectedTeacher.getSchoolName();
                }
            }
        }));
        schoolTeacher.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                Teacher teacher = event.getRowValue();
                if (test>0){
                    try {
                        userModel.editTeacher(teacher,newSchool);
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });

    }

    private void OnSchoolEditException(String exceptionMessage, String instructions) {
        selectedTeacher =teachersTableView.getSelectionModel().getSelectedItem();
        test = -1;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText(exceptionMessage);
        alert.setContentText(instructions);
        alert.showAndWait();
    }

    private void ExceptionOnEdit(UserException e) {
        test=-1;
        selectedTeacher = teachersTableView.getSelectionModel().getSelectedItem();
        selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText(e.getExceptionMessage());
        alert.setContentText(e.getInstructions());
        alert.showAndWait();
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

    public void addStudentLV(Student student){
        if (allSchoolsLV.getSelectionModel().getSelectedItem().getName().equals(student.getSchoolName())){
            allStudentsSchool.getItems().add(student.getFirstName()+" "+student.getLastName());
        }
    }

    public void addTeacherLV(Teacher teacher){
        if (allSchoolsLV.getSelectionModel().getSelectedItem().getName().equals(teacher.getSchoolName())){
            allTeachersSchool.getItems().add(teacher.getFirstName()+" "+teacher.getLastName());
        }
    }

}
