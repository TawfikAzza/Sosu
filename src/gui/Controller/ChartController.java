package gui.Controller;

import be.Citizen;
import be.Observation;
import be.ObservationType;
import gui.Model.ObservationModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ChartController implements Initializable {
    @FXML
    private DatePicker fromDP,toDP;
    @FXML
    private AreaChart areaChart;

    private Citizen currentCitizen;

    private ObservationType observationType;

    private ObservationModel observationModel;

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
            observationModel = new ObservationModel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fromDP.setEditable(false);
        toDP.setEditable(false);

        fromDP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    drawChart();
                } catch (SQLException e) {
                    e.printStackTrace();
                }catch (NullPointerException ignored){}
            }
        });

        toDP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    drawChart();
                } catch (SQLException e) {
                    e.printStackTrace();
                }catch (NullPointerException ignored){}
            }
        });
    }

    public void drawChart() throws SQLException {
        LocalDate fromDpValue= fromDP.getValue();
        LocalDate toDpValue=toDP.getValue();

        if (fromDpValue.compareTo(toDpValue) > 0) {
            LocalDate intermediateValue = fromDpValue;
            fromDpValue = toDpValue;
            toDpValue = intermediateValue;
        }

        List<Observation> allObservations = observationModel.getAllObservations(observationType, currentCitizen, fromDpValue, toDpValue);

        XYChart.Series series = new XYChart.Series();
        for (Observation observation : allObservations)
            series.getData().add(new XYChart.Data<>(observation.getObservation_date().toString(),observation.getMeasurement()));
            areaChart.getData().add(series);
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
                        try {
                            setDisable(empty || item.compareTo(firstDate) < 0 || item.compareTo(LocalDate.now()) > 0);
                        }catch (NullPointerException npe){
                            setDisable(empty || item.compareTo(LocalDate.now()) < 0 || item.compareTo(LocalDate.now()) > 0);

                        }
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
