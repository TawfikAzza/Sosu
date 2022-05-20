package gui.Controller;

import be.School;
import be.Student;
import be.Teacher;
import be.User;
import bll.exceptions.UserException;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {
    @FXML
    private TextField searchUsersField;
    @FXML
    private TableView usersTV;
    @FXML
    private TableColumn firstNameTC,lastNameTC,userNameTC,passwordTC,emailTC;
    @FXML
    private TableColumn phoneNumberTC;

    private LoginLogoutUtil.UserType userType;
    private UserModel userModel;
    private final UserException ue = new UserException();
    private Integer test = 1;



    public ManageUsersController(LoginLogoutUtil.UserType userType) throws IOException, UserException {
        this.userType=userType;
        userModel = UserModel.getInstance();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUsersTV();
        searchUsersField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        if (userType== LoginLogoutUtil.UserType.TEACHER)
                            usersTV.setItems(userModel.getAllTeachers(searchUsersField.getText()));
                        else
                            usersTV.setItems(userModel.getAllStudents(searchUsersField.getText()));
                    } catch (SQLException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
            }
        }
    });
    }

    private void initializeUsersTV() {
        usersTV.setEditable(true);
        initializeFnameColumn();
        initializeLnameColumn();
        initializeUserNameColumn();

        passwordTC.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberTC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    private void initializeUserNameColumn() {
        userNameTC.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserUName(string,userModel.userNameTaken(string));
                } catch (UserException | SQLException e) {
                    test=-1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    //alert.setHeaderText(e.getExceptionMessage());
                    //alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType== LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher)usersTV.getSelectionModel().getSelectedItem()).getUserName();
                    else return ((Student)usersTV.getSelectionModel().getSelectedItem()).getUserName();                }
                return string;
            }
            }
        ));
        userNameTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                Teacher teacher;
                Student student;
                Object object = event.getRowValue();
                if (userType== LoginLogoutUtil.UserType.TEACHER){
                    teacher = Teacher.class.cast(object);
                if (test>0)
                {
                    teacher.setUserName((String) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
                else{ student = Student.class.cast(object);
                    student.setUserName((String) event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
                }
        });
    }

    private void initializeLnameColumn() {
        lastNameTC.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserLN(string);
                } catch (UserException e) {
                    test=-1;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType== LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher)usersTV.getSelectionModel().getSelectedItem()).getLastName();
                    else return ((Student)usersTV.getSelectionModel().getSelectedItem()).getLastName();                }
                return string;
            }
        }));
        lastNameTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            Teacher teacher;
            Student student;
            Object object = event.getRowValue();
            if (userType== LoginLogoutUtil.UserType.TEACHER){
                teacher = Teacher.class.cast(object);
                if (test>0){
                    teacher.setLastName((String) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
            else {student = Student.class.cast(object);
                if (test>0){
                    student.setLastName((String) event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                test=1;
            }
        });
    }

    private void initializeFnameColumn() {
        firstNameTC.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserFN(string);
                } catch (UserException e) {
                    test=-1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType== LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher)usersTV.getSelectionModel().getSelectedItem()).getFirstName();
                    else return ((Student)usersTV.getSelectionModel().getSelectedItem()).getFirstName();
                }
                return string;
            }}));
            firstNameTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
                Teacher teacher;
                Student student;
                Object object = event.getRowValue();
                if (userType== LoginLogoutUtil.UserType.TEACHER){
                    teacher = Teacher.class.cast(object);
                    if (test>0){
                        teacher.setFirstName((String) event.getNewValue());
                        try {
                            userModel.editTeacher(teacher,new School(teacher.getSchoolId(),teacher.getSchoolName()));
                        } catch (UserException e) {
                            e.printStackTrace();
                        }
                    }
                    test=1;
                }
                else {student = Student.class.cast(object);
                    if (test>0){
                        student.setFirstName((String) event.getNewValue());
                        try {
                            userModel.editStudent(new School(student.getSchoolId(),student.getSchoolName()),student);
                        } catch (UserException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    test=1;
                }
            });
        }

    public void deleteUser(ActionEvent actionEvent) throws SQLException {
        if (usersTV.getSelectionModel().getSelectedItem()!=null){
            if (userType== LoginLogoutUtil.UserType.TEACHER)
                userModel.deleteTeacher((Teacher) usersTV.getSelectionModel().getSelectedItem());
            else userModel.deleteStudent((Student) usersTV.getSelectionModel().getSelectedItem());
        }

    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        if (usersTV.getSelectionModel().getSelectedItem()!=null){
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            NewEditUserController newEditUserController = loader.getController();
            if (userType== LoginLogoutUtil.UserType.STUDENT)
                newEditUserController.editStudent((Student) usersTV.getSelectionModel().getSelectedItem());
            else
                newEditUserController.editTeacher((Teacher) usersTV.getSelectionModel().getSelectedItem());


            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        if (userType== LoginLogoutUtil.UserType.STUDENT)
        newEditUserController.newStudent();

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
