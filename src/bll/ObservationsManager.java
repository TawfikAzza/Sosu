package bll;

import be.*;
import dal.db.measurementDAO.ObservationsFacade;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ObservationsManager {
    ObservationsFacade observationsFacade;

    public ObservationsManager() throws IOException {
        this.observationsFacade = new ObservationsFacade();
    }

    public List<BPMeasurement> getAllBPMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return observationsFacade.getAllBPMeasurements(citizen, startDate, endDate);
    }

    public List<BSMeasurement> getAllBSMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return observationsFacade.getAllBSMeasurements(citizen, startDate, endDate);
    }

    public List<OxMeasurement> getAllOxygenMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return observationsFacade.getAllOxygenMeasurements(citizen, startDate, endDate);
    }

    public List<TempMeasurement> getAllTemperatureMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return observationsFacade.getAllTemperatureMeasurements(citizen, startDate, endDate);
    }

    public void addBPMeasurement(Citizen citizen, float measurement) throws SQLException {
        observationsFacade.addBPMeasurement(citizen, measurement);
    }

    public void addBSMeasurement(Citizen citizen, float measurement) throws SQLException {
        observationsFacade.addBSMeasurement(citizen, measurement);
    }

    public void addOxygenMeasurement(Citizen citizen, int measurement) throws SQLException {
        observationsFacade.addOxygenMeasurement(citizen, measurement);
    }

    public void addTemperature(Citizen citizen, float measurement) throws SQLException {
        observationsFacade.addTemperature(citizen, measurement);
    }
}
