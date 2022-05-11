package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayGRIController implements Initializable {

    @FXML
    private TextArea textBoligens, textHelbred, textHjælpe , textLivhistorie , textMestring , textMotivation;
    @FXML
    private TextArea textNetværk , textRessourcer , textRoller ,textUddannelse , textVaner;
    @FXML
    private Label lblAdress, lblBirthdate, lblFName, lblLName, lblPhone, lblSchool;
    @FXML
    private Button btnClose;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
            window.close();
        }


}
