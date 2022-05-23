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
import javafx.scene.layout.GridPane;
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
    private TableColumn firstNameTC, lastNameTC, userNameTC, passwordTC, emailTC;
    @FXML
    private TableColumn phoneNumberTC;

    private LoginLogoutUtil.UserType userType;
    private UserModel userModel;
    private final UserException ue = new UserException();
    private Integer test = 1;


    public ManageUsersController(LoginLogoutUtil.UserType userType) throws IOException, UserException {
        this.userType = userType;
        userModel = UserModel.getInstance();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUsersTV();
        searchUsersField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        if (userType == LoginLogoutUtil.UserType.TEACHER)
                            usersTV.setItems(userModel.getAllTeachers(searchUsersField.getText()));
                        else if (userType== LoginLogoutUtil.UserType.STUDENT)
                            usersTV.setItems(userModel.getAllStudents(searchUsersField.getText()));
                        else
                            usersTV.setItems(userModel.getAllAdmins(searchUsersField.getText()));
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
        initializePasswordColumn();
        initializeEmailColumn();
        initializePhoneNumberColumn();
        if(userType== LoginLogoutUtil.UserType.ADMIN)
            usersTV.getColumns().add(new TableColumn<>("school"));
    }

    private void initializePhoneNumberColumn() {
        phoneNumberTC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    ue.checkPhoneNumber(string);
                } catch (UserException nfe) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(nfe.getExceptionMessage());
                    alert.setContentText(nfe.getInstructions());
                    alert.showAndWait();
                    if (userType == LoginLogoutUtil.UserType.TEACHER) {
                        return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getPhoneNumber();
                    } else {
                        return ((Student) usersTV.getSelectionModel().getSelectedItem()).getPhoneNumber();
                    }
                }
                return test;
            }
        }));
        phoneNumberTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            Object object = event.getRowValue();
            Teacher teacher;
            Student student;
            if (userType == LoginLogoutUtil.UserType.TEACHER) {
                teacher = Teacher.class.cast(object);
                if (test > 0) {
                    teacher.setPhoneNumber((Integer) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                    } catch (UserException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }} else {
                    student = Student.class.cast(object);
                    student.setPhoneNumber((Integer) event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                    } catch (UserException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            test = 1;
        });
    }

    private void initializeEmailColumn() {
        emailTC.setCellValueFactory(new PropertyValueFactory<>("emailProperty"));
        emailTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkEmail(string);
                } catch (UserException e) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType == LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getEmail();
                    else return ((Student) usersTV.getSelectionModel().getSelectedItem()).getEmail();
                }
                return string;
            }
        }));
        emailTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event1 -> {
            Object object = event1.getRowValue();
            Teacher teacher;
            Student student;
            if (userType == LoginLogoutUtil.UserType.TEACHER) {
                teacher = Teacher.class.cast(object);
                if (test > 0) {
                    teacher.setEmail((String) event1.getNewValue());
                    try {
                        userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                    } catch (UserException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            } else {
                    student = Student.class.cast(object);
                    student.setEmail((String) event1.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                    } catch (UserException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            test = 1;
        });
    }

    private void initializePasswordColumn() {
        passwordTC.setCellValueFactory(new PropertyValueFactory<>("passwordProperty"));
        passwordTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    ue.checkUserPassword(string);
                } catch (UserException e) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();

                    if (userType == LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getPassWord();
                    else return ((Student) usersTV.getSelectionModel().getSelectedItem()).getPassWord();
                }
                return string;
            }
        }));
        passwordTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                Object object = event.getRowValue();
                Teacher teacher;
                Student student;
                if (userType == LoginLogoutUtil.UserType.TEACHER) {
                    teacher = Teacher.class.cast(object);
                    if (test > 0) {
                        teacher.setPassWord((String) event.getNewValue());
                        try {
                            userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                        } catch (UserException e) {
                            DisplayMessage.displayError(e);
                            e.printStackTrace();
                        }
                    }} else {
                        student = Student.class.cast(object);
                        student.setPassWord((String) event.getNewValue());
                        try {
                            userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                        } catch (UserException e) {
                            DisplayMessage.displayError(e);
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                test = 1;
            }
        });
    }

    private void initializeUserNameColumn() {
        userNameTC.setCellValueFactory(new PropertyValueFactory<>("userNameProperty"));
        userNameTC.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
                                                                        @Override
                                                                        public String toString(String object) {
                                                                            return object;
                                                                        }

                                                                        @Override
                                                                        public String fromString(String string) {
                                                                            try {
                                                                                ue.checkUserUName(string, userModel.userNameTaken(string));
                                                                            } catch (UserException | SQLException e) {
                                                                                test = -1;
                                                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                                                alert.setTitle("Alert");
                                                                                //alert.setHeaderText(e.getExceptionMessage());
                                                                                //alert.setContentText(e.getInstructions());
                                                                                alert.showAndWait();
                                                                                if (userType == LoginLogoutUtil.UserType.TEACHER)
                                                                                    return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getUserName();
                                                                                else return ((Student) usersTV.getSelectionModel().getSelectedItem()).getUserName();
                                                                            }
                                                                            return string;
                                                                        }
                                                                    }
        ));
        userNameTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            Teacher teacher;
            Student student;
            Object object = event.getRowValue();
            if (userType == LoginLogoutUtil.UserType.TEACHER) {
                teacher = Teacher.class.cast(object);
                if (test > 0) {
                    teacher.setUserName((String) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                student = Student.class.cast(object);
                student.setUserName((String) event.getNewValue());
                try {
                    userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                } catch (UserException | SQLException e) {
                    e.printStackTrace();
                }
            }
            test = 1;
        });
    }

    private void initializeLnameColumn() {
        lastNameTC.setCellValueFactory(new PropertyValueFactory<>("lNameProperty"));
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
                    test = -1;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType == LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getLastName();
                    else return ((Student) usersTV.getSelectionModel().getSelectedItem()).getLastName();
                }
                return string;
            }
        }));
        lastNameTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            Teacher teacher;
            Student student;
            Object object = event.getRowValue();
            if (userType == LoginLogoutUtil.UserType.TEACHER) {
                teacher = Teacher.class.cast(object);
                if (test > 0) {
                    teacher.setLastName((String) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                student = Student.class.cast(object);
                if (test > 0) {
                    student.setLastName((String) event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }}
                test = 1;
        });
    }

    private void initializeFnameColumn() {
        firstNameTC.setCellValueFactory(new PropertyValueFactory<>("fNameProperty"));
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
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                    if (userType == LoginLogoutUtil.UserType.TEACHER)
                        return ((Teacher) usersTV.getSelectionModel().getSelectedItem()).getFirstName();
                    else return ((Student) usersTV.getSelectionModel().getSelectedItem()).getFirstName();
                }
                return string;
            }
        }));
        firstNameTC.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            Teacher teacher;
            Student student;
            Object object = event.getRowValue();
            if (userType == LoginLogoutUtil.UserType.TEACHER) {
                teacher = Teacher.class.cast(object);
                if (test > 0) {
                    teacher.setFirstName((String) event.getNewValue());
                    try {
                        userModel.editTeacher(teacher, new School(teacher.getSchoolId(), teacher.getSchoolName()));
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                student = Student.class.cast(object);
                if (test > 0) {
                    student.setFirstName((String) event.getNewValue());
                    try {
                        userModel.editStudent(new School(student.getSchoolId(), student.getSchoolName()), student);
                    } catch (UserException | SQLException e) {
                        e.printStackTrace();
                    }
                }}
                test = 1;
        });
    }


    public void deleteUser(ActionEvent actionEvent) throws SQLException {
        if (usersTV.getSelectionModel().getSelectedItem() != null) {
            if (userType == LoginLogoutUtil.UserType.TEACHER)
                userModel.deleteTeacher((Teacher) usersTV.getSelectionModel().getSelectedItem());
            else userModel.deleteStudent((Student) usersTV.getSelectionModel().getSelectedItem());
        }

    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        if (usersTV.getSelectionModel().getSelectedItem() != null) {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            NewEditUserController newEditUserController = new NewEditUserController(userType);
            loader.setController(newEditUserController);

            root = loader.load();

            newEditUserController.isNewUser(false, (User) usersTV.getSelectionModel().getSelectedItem());


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader newUserLoader = new FXMLLoader(getClass().getResource("/gui/View/NewEditUser.fxml"));
        newUserLoader.setController(new NewEditUserController(userType));

        Parent root = newUserLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
