package gui.Controller;

import be.Citizen;
import be.ObservationType;
import bll.exceptions.ObservationException;
import bll.util.GlobalVariables;
import gui.Model.ObservationModel;
import gui.utils.DisplayMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private ObservationModel observationModel;
    @FXML
    private TextField bloodPressureTF,bloodSugarTF,oxygenTF,temperatureTF,wightTF,heartBeatTF;
    @FXML
    private Button cancelBtn;

    @FXML
    private void handleVSBP(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.BPMeasurement);
    }

    @FXML
    private void handleVSOxygen(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.OxyMeasurement);
    }

    @FXML
    private void handleVSTemp(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.TempMeasurement);
    }

    @FXML
    private void handleVSWeight(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.WeightMeasurement);
    }

    @FXML
    private void handleVSHB(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.HeartBeatMeasurement);
    }

    @FXML
    private void handleVSBS(ActionEvent actionEvent) throws IOException {
        openChartWindow(ObservationType.BSMeasurement);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            observationModel = new ObservationModel();
            selectedCitizen = GlobalVariables.getSelectedCitizen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TextField> textFields = new ArrayList<>(Arrays. asList(bloodPressureTF,bloodSugarTF,oxygenTF,temperatureTF,wightTF,heartBeatTF));
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
                    }catch (ObservationException oe){
                            DisplayMessage.displayObservationErrorMessage(oe);
                        }}
                }
            });
        }
        ArrayList<TextField> digits3TextFields = new ArrayList<>(Arrays. asList(bloodPressureTF,oxygenTF,wightTF,oxygenTF,heartBeatTF));
        for (TextField textField : digits3TextFields)
             limitDigitsTF(textField,3);
        limitDigitsTF(temperatureTF,2);
        limitDigitsTF(bloodSugarTF,1);

    }


    private void createNewObservation(TextField textField,Citizen citizen,float measurement)throws SQLException,ObservationException {
        ObservationType observationType;
        if (textField.equals(bloodPressureTF))
            observationType = ObservationType.BPMeasurement;
        else if (textField.equals(bloodSugarTF))
            observationType=ObservationType.BSMeasurement;
        else if (textField.equals(oxygenTF))
            observationType=ObservationType.OxyMeasurement;
        else if (textField.equals(temperatureTF))
            observationType=ObservationType.TempMeasurement;
        else if (textField.equals(wightTF))
            observationType=ObservationType.WeightMeasurement;
        else observationType= ObservationType.HeartBeatMeasurement;

        observationModel.addObservation(observationType,citizen,measurement);

    }
    @FXML
    private void openChartWindow(ObservationType observationType) throws IOException {
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
    private void limitDigitsTF (TextField textField, int digits){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (textField.getText().length() > digits) {
                    String s = textField.getText().substring(0, digits);
                    textField.setText(s);
                }
            }
        });
    }

    @FXML
    private void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
