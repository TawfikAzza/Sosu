package gui.Controller;

import be.School;
import be.Teacher;
import com.jfoenix.controls.JFXComboBox;
import gui.Model.SchoolModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEditUserController implements Initializable {
    @FXML
    private Text mainLabel;
    @FXML
    private TextField firstName,lastName,userName,passWord,email,phoneNumberField;
    @FXML
    private JFXComboBox<School> schoolComboBox;

    private Boolean newUser;
    private Boolean isTeacher;

    private SchoolModel schoolModel;

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void createNewUser(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}
