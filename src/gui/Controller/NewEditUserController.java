package gui.Controller;

import be.School;
import be.Student;
import be.Teacher;
import be.User;
import bll.exceptions.UserException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import gui.Model.SchoolModel;
import gui.Model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEditUserController implements Initializable {
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

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void createNewUser(ActionEvent actionEvent) throws SQLException {
        if(newUser&&isTeacher)
            try {
                Teacher newTeacher = userModel.newTeacher(schoolComboBox.getSelectionModel().getSelectedItem(),
                        firstName.getText(),lastName.getText(),userName.getText(),passWord.getText(),email.getText(),phoneNumberField.getText());

                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }

        else if(newUser&&!isTeacher)
            userModel.newStudent(schoolComboBox.getSelectionModel().getSelectedItem(),
                    firstName.getText(),lastName.getText(),userName.getText(),passWord.getText(),email.getText(),Integer.parseInt(phoneNumberField.getText()));

        else if (!newUser&&isTeacher){
            teacher.setFirstName(firstName.getText());
            teacher.setLastName(lastName.getText());
            teacher.setUserName(userName.getText());
            teacher.setPassWord(passWord.getText());
            teacher.setEmail(email.getText());
            teacher.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
            try {
                userModel.editTeacher(teacher,schoolComboBox.getSelectionModel().getSelectedItem());
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
            userModel.editStudent(student);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            schoolComboBox.getItems().setAll(schoolModel.getAllSchools());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
