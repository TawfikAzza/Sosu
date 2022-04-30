package gui.Controller;

import be.School;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewCitizenController {
    @FXML
    private TextField firstName,lastName,cprNumber,address,phoneNumber;

    public JFXComboBox<School> chooseSchool;

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleConfirm(ActionEvent actionEvent) {
    }
}
