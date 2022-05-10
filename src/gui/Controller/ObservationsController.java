package gui.Controller;

import be.Citizen;
import be.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ObservationsController implements Initializable {

    private Citizen selectedCitizen;
    @FXML
    private TextField bloodPressureTF,bloodSugarTF,oxygenTF,temperatureTF,wightTF,heightTF;

    public void handleVSBloodPressure(ActionEvent actionEvent) {
    }

    public void handleVSBloodSugar(ActionEvent actionEvent) {
    }

    public void handleVSOxygen(ActionEvent actionEvent) {
    }

    public void handleVSTemperature(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCitizen(Citizen selectedItem) {
        selectedCitizen=selectedItem;
    }
}
