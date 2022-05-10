package gui.Model;

import be.*;
import bll.ObservationsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ObservationModel {
    ObservationsManager observationsManager;
    ObservableList<TempMeasurement> allTempMeasurements;
    ObservableList<BPMeasurement> allBPMeasurements;
    ObservableList<BSMeasurement> allBSMeasurements;
    ObservableList<OxMeasurement> allOxMeasurements;


    public ObservationModel() throws IOException {
        observationsManager= new ObservationsManager();
    }
    public List<TempMeasurement>getAllTempMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        allTempMeasurements=FXCollections.observableArrayList();
        allTempMeasurements.addAll(observationsManager.getAllTemperatureMeasurements(citizen,startDate,endDate));
        return allTempMeasurements;
    }

    public List<BPMeasurement>getAllBPMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        allBPMeasurements=FXCollections.observableArrayList();
        allBPMeasurements.addAll(observationsManager.getAllBPMeasurements(citizen,startDate,endDate));
        return allBPMeasurements;
    }

    public List<BSMeasurement>getAllBSMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        allBSMeasurements=FXCollections.observableArrayList();
        allBSMeasurements.addAll(observationsManager.getAllBSMeasurements(citizen,startDate,endDate));
        return allBSMeasurements;
    }

    public List<OxMeasurement>getAllOxMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        allOxMeasurements=FXCollections.observableArrayList();
        allOxMeasurements.addAll(observationsManager.getAllOxygenMeasurements(citizen,startDate,endDate));
        return allOxMeasurements;
    }

    public void addBPMeasurement(Citizen citizen,float bloodPressure) throws SQLException {
        observationsManager.addBPMeasurement(citizen,bloodPressure);
    }

    public void addBSMeasurement(Citizen citizen,float bloodSugar) throws SQLException {
        observationsManager.addBSMeasurement(citizen,bloodSugar);
    }

    public void addTempMeasurement(Citizen citizen,float temperature) throws SQLException {
        observationsManager.addTemperature(citizen,temperature);
    }

    public void addOxMeasurement(Citizen citizen,int oxygen) throws SQLException {
        observationsManager.addBPMeasurement(citizen,oxygen);
    }

}
