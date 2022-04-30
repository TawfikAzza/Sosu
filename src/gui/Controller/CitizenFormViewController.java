package gui.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CitizenFormViewController implements Initializable {

    @FXML
    private TextField fNameField;

    @FXML
    private TextField lNAmeField;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox templateCheckBox;

    private int schoolID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }
}
