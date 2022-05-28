package gui.Model;

import be.Citizen;
import be.Observation;
import be.ObservationType;
import bll.ObservationManager;
import bll.exceptions.ObservationException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ObservationModel {

    private ObservationManager observationManager;
    private ObservableList<Observation> allObservations;

    public ObservationModel() throws IOException {
        observationManager= new ObservationManager();
    }

    public void addObservation(ObservationType observationType, Citizen citizen, float measurement) throws SQLException, ObservationException {
        observationManager.addObservation(observationType,citizen,measurement);
    }

    public List<Observation>getAllObservations(ObservationType observationType, Citizen citizen, LocalDate fDay, LocalDate lDay)throws SQLException{
        return observationManager.getAllObservations(observationType,citizen,fDay,lDay);
    }

    public LocalDate getFirstObservationDate(ObservationType observationType, Citizen currentCitizen) throws SQLException {
        return observationManager.getFirstObservationDate(observationType,currentCitizen);
    }
}
