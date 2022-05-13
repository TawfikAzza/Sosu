package gui.Controller;

import be.Citizen;
import be.ObservationType;
import gui.Model.ObservationModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ObservationsController implements Initializable {

    private Citizen selectedCitizen;
    @FXML
    private TextField bloodPressureTF,bloodSugarTF,oxygenTF,temperatureTF,wightTF,heightTF;
    ObservationModel observationModel;

    public void handleVSBP(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.BPMeasurement);
    }

    public void handleVSBS(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.BSMeasurement);
    }

    public void handleVSOxygen(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.OxyMeasurement);
    }

    public void handleVSTemp(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.TempMeasurement);
    }
    public void handleVSWeight(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.WeightMeasurement);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            observationModel = new ObservationModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TextField> textFields = new ArrayList<>(Arrays. asList(bloodPressureTF,bloodSugarTF,oxygenTF,temperatureTF,wightTF));
        for (TextField textField : textFields){
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)){
                        try {
                        createNewObservation(textField,selectedCitizen, Float.parseFloat(textField.getText()));
                        textField.setText("");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }}
                }
            });
        }
    }

    public void setCitizen(Citizen selectedItem) {
        selectedCitizen=selectedItem;
    }

    public void createNewObservation(TextField textField,Citizen citizen,float measurement)throws SQLException {
        ObservationType observationType;
        if (textField.equals(bloodPressureTF))
            observationType = ObservationType.BPMeasurement;
        else if (textField.equals(bloodSugarTF))
            observationType=ObservationType.BSMeasurement;
        else if (textField.equals(oxygenTF))
            observationType=ObservationType.OxyMeasurement;
        else if (textField.equals(temperatureTF))
            observationType=ObservationType.TempMeasurement;
        else observationType=ObservationType.WeightMeasurement;

        observationModel.addObservation(observationType,citizen,measurement);
    }

    public void openChartWindow(ObservationType observationType) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ChartView.fxml"));
        root = loader.load();

        ChartController chartController = loader.getController();
        chartController.setObservationType(observationType);
        chartController.setCitizen(selectedCitizen);
        chartController.setLimitsDatePicker(chartController.getFirstObservationDate(observationModel));

        Stage stage = new Stage();
        stage.setTitle(observationType.name()+ "chart");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
