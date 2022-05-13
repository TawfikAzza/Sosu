package gui.Controller;

import be.Citizen;
import be.ObservationType;
import gui.Model.ObservationModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    @FXML
    private DatePicker fromDP,toDP;
    @FXML
    private AreaChart areaChart;

    private Citizen currentCitizen;

    private ObservationType observationType;

    ObservationModel observationModel;

    public void setObservationType(ObservationType observationType) {
        this.observationType =observationType;
        areaChart.setTitle("Chart representing the evolution of "+observationType.name()+" in function of time.");
        areaChart.getXAxis().setLabel("Time");
        areaChart.getYAxis().setLabel(observationType.name());
    }

    public void setCitizen(Citizen selectedCitizen) {
        currentCitizen= selectedCitizen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            observationModel= new ObservationModel();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setLimitsDatePicker(LocalDate firstDate) {
        ArrayList<DatePicker> datePickers = new ArrayList<>(Arrays. asList(fromDP,toDP));
        for (DatePicker datePicker : datePickers){
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(empty || item.compareTo(firstDate) < 0 || item.compareTo(LocalDate.now()) > 0);
                    }
                };
            }
        });
        }
    }

    public LocalDate getFirstObservationDate(ObservationModel observationModel){
        try {
            return observationModel.getFirstObservationDate(observationType,currentCitizen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
