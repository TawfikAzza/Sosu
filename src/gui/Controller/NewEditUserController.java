package gui.Controller;

import be.School;
import be.Student;
import be.Teacher;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import gui.Model.SchoolModel;
import gui.Model.UserModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewEditUserController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton cnfrmButton;
    @FXML
    private Text mainLabel;
    @FXML
    private TextField firstName,lastName,userName,passWord,email,phoneNumberField;
    @FXML
    private JFXComboBox<School> schoolComboBox;

    private Boolean newUser=true;
    private Boolean isTeacher=true;

    private SchoolModel schoolModel;
    private UserModel userModel;

    private Teacher teacher;
    private Student student;

    private ObservableList<Teacher>allTeacher=FXCollections.observableArrayList();
    private ObservableList<Student>allStudents=FXCollections.observableArrayList();

    private String init;
    AdminViewController adminViewController;

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage;
        if (!(firstName.getText().isEmpty()&&lastName.getText().isEmpty()&&userName.getText().isEmpty()&&passWord.getText().isEmpty()&&email.getText().isEmpty()&&phoneNumberField.getText().isEmpty()&&schoolComboBox.getSelectionModel().getSelectedItem()==null))
        {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Are you sure you want to close this window ?");
        alert.setContentText("All your infos will be lost in this case.");
        alert.showAndWait();
        if (alert.showAndWait().get() == ButtonType.OK) {
             stage = (Stage) cnfrmButton.getScene().getWindow();
            stage.close();
        }}
        else
        { stage = (Stage) cnfrmButton.getScene().getWindow();
            stage.close();}
    }

    public void createNewUser(ActionEvent actionEvent) throws SQLException {
        if(newUser&&isTeacher)
            try {
                Teacher newTeacher = userModel.newTeacher(schoolComboBox.getSelectionModel().getSelectedItem(),
                        firstName.getText(),lastName.getText(),userName.getText(),passWord.getText(),email.getText(),phoneNumberField.getText());
                adminViewController.addTeacherLV(newTeacher);
                if (newTeacher.getFirstName().toLowerCase().contains(init)||newTeacher.getLastName().toLowerCase(Locale.ROOT).contains(init.toLowerCase(Locale.ROOT)))
                {   allTeacher.add(newTeacher);
                    adminViewController.refreshTView(allTeacher);}
                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }

        else if(newUser&&!isTeacher){
            try {
                Student student = userModel.newStudent(schoolComboBox.getSelectionModel().getSelectedItem(),
                        firstName.getText(),lastName.getText(),userName.getText(),passWord.getText(),email.getText(),phoneNumberField.getText());
                adminViewController.addStudentLV(student);
                if (student.getFirstName().toLowerCase().contains(init)||student.getLastName().toLowerCase(Locale.ROOT).contains(init.toLowerCase(Locale.ROOT)))
                {   allStudents.add(student);
                    adminViewController.refreshTViewStudents(allStudents);}
                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }
        }

        else if (!newUser&&isTeacher){
            teacher.setFirstName(firstName.getText());
            teacher.setLastName(lastName.getText());
            teacher.setUserName(userName.getText());
            teacher.setPassWord(passWord.getText());
            teacher.setEmail(email.getText());
            teacher.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
            try {
                userModel.editTeacher(teacher,schoolComboBox.getSelectionModel().getSelectedItem());
                if (!(teacher.getFirstName().toLowerCase().contains(init)||teacher.getLastName().toLowerCase(Locale.ROOT).contains(init.toLowerCase(Locale.ROOT))))
                {   allTeacher.remove(teacher);
                    adminViewController.refreshTView(allTeacher);}
                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }
        }
        else {
            student.setFirstName(firstName.getText());
            student.setLastName(lastName.getText());
            student.setUserName(userName.getText());
            student.setPassWord(passWord.getText());
            student.setEmail(email.getText());
            student.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
            try {
                userModel.editStudent(schoolComboBox.getSelectionModel().getSelectedItem(),student);
                if (!(student.getFirstName().toLowerCase().contains(init)||student.getLastName().toLowerCase(Locale.ROOT).contains(init.toLowerCase(Locale.ROOT))))
                {   allStudents.remove(student);
                    adminViewController.refreshTViewStudents(allStudents);}
                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userModel = new UserModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            schoolModel = new SchoolModel();
        } catch (IOException | SchoolException e) {
            e.printStackTrace();
        }
        try {
            schoolComboBox.getItems().setAll(schoolModel.getAllSchools());
        } catch (SQLException | SchoolException e) {
            e.printStackTrace();
        }

        phoneNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (phoneNumberField.getText().length() > 8) {
                    String s = phoneNumberField.getText().substring(0, 8);
                    phoneNumberField.setText(s);
                }
            }
        });

        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        createNewUser(new ActionEvent());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else if(event.getCode().equals(KeyCode.ESCAPE)){
                    handleCancel(new ActionEvent());}
            }
        });
    }

    public void editTeacher(Teacher selectedItem) {
        teacher = selectedItem;
        mainLabel.setText("Edit teacher");
        newUser=false;
        firstName.setText(selectedItem.getFirstName());
        lastName.setText(selectedItem.getLastName());
        userName.setText(selectedItem.getUserName());
        passWord.setText(selectedItem.getPassWord());
        email.setText(selectedItem.getEmail());
        phoneNumberField.setText(String.valueOf(selectedItem.getPhoneNumber()));
        for (School school : schoolComboBox.getItems()){
            if (school.getName().equals(selectedItem.getSchoolName())){
                int index = schoolComboBox.getItems().indexOf(school);
                schoolComboBox.getSelectionModel().select(index);
            }
        }
    }

    public void editStudent(Student selectedItem) {
        student = selectedItem;
        mainLabel.setText("Edit student");
        newUser=false;
        isTeacher=false;
        firstName.setText(selectedItem.getFirstName());
        lastName.setText(selectedItem.getLastName());
        userName.setText(selectedItem.getUserName());
        passWord.setText(selectedItem.getPassWord());
        email.setText(selectedItem.getEmail());
        phoneNumberField.setText(String.valueOf(selectedItem.getPhoneNumber()));
        for (School school : schoolComboBox.getItems()){
            if (school.getName().equals(selectedItem.getSchoolName())){
                int index = schoolComboBox.getItems().indexOf(school);
                schoolComboBox.getSelectionModel().select(index);
            }
        }
    }

    public void updateTView(ObservableList<Teacher> allTeacherFiltered, String text) {
        allTeacher=allTeacherFiltered;
        init=text;
    }

    public void setController(AdminViewController adminViewController) {
        this.adminViewController=adminViewController;
    }

    public void updateTViewStudent(ObservableList<Student> allStudentsFiltered, String text) {
        allStudents=allStudentsFiltered;
        init=text;
    }

    public void newStudent() {
        isTeacher=false;
        mainLabel.setText("New student");
    }
}
