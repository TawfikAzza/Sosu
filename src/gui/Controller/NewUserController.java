package gui.Controller;

import be.School;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewUserController {
    @FXML
    private TextField firstName,lastName,userName,passWord,email,phoneNumberField;
    @FXML
    private JFXComboBox<School> schoolComboBox;

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void createNewUser(ActionEvent actionEvent) {
    }


}
