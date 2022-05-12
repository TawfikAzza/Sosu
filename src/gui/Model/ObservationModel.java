package gui.Model;

import be.Citizen;
import be.Observation;
import be.ObservationType;
import be.School;
import bll.ObservationManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ObservationModel {

    ObservationManager observationManager;
    ObservableList<Observation> allObservations;

    public ObservationModel() throws IOException {
        observationManager= new ObservationManager();
    }

    public void addObservation(ObservationType observationType, Citizen citizen, float measurement) throws SQLException {
        observationManager.addObservation(observationType,citizen,measurement);
    }

    public List<Observation>getAllObservations(ObservationType observationType, Citizen citizen, LocalDate fDay, LocalDate lDay)throws SQLException{
        return observationManager.getAllObservations(observationType,citizen,fDay,lDay);
    }

}
