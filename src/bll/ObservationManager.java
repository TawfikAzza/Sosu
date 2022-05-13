package bll;

import be.Citizen;
import be.Observation;
import be.ObservationType;
import dal.db.ObservationDao;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ObservationManager {
    ObservationDao observationDao;
    public ObservationManager() throws IOException {
        this.observationDao= new ObservationDao();
    }

    public List<Observation>getAllObservations(ObservationType observationType, Citizen citizen, LocalDate fDay, LocalDate lDay) throws SQLException {
        return observationDao.getAllObservations(observationType,citizen,fDay,lDay);
    }

    public void addObservation(ObservationType observationType, Citizen citizen, float measurement) throws SQLException {
         observationDao.newObservation(observationType,citizen,measurement);
    }

    public LocalDate getFirstObservationDate(ObservationType observationType, Citizen currentCitizen) throws SQLException {
        return observationDao.getFirstObservationDate(observationType,currentCitizen);
    }
}
