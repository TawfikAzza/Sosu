package dal.db.measurementDAO;

import be.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ObservationsFacade {
    BPMeasurementDAO bloodPressureMeasurementDAO;
    BSMeasurementDAO bloodSugarMeasurementDAO;
    OxMeasurementDAO oxygenMeasurementDAO ;
    TempMeasurementDAO temperatureMeasurementDAO;
    BMIMeasurementAO bmiMeasurementAO;

    public ObservationsFacade() throws IOException {
        bloodPressureMeasurementDAO =new BPMeasurementDAO();
        bloodSugarMeasurementDAO = new BSMeasurementDAO();
        oxygenMeasurementDAO = new OxMeasurementDAO();
        temperatureMeasurementDAO = new TempMeasurementDAO();
        bmiMeasurementAO = new BMIMeasurementAO();
    }

    public List<BPMeasurement>getAllBPMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return bloodPressureMeasurementDAO.getAllMeasurements(citizen,startDate,endDate);
    }

    public List<BSMeasurement>getAllBSMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return bloodSugarMeasurementDAO.getAllMeasurements(citizen,startDate,endDate);
    }

    public List<OxMeasurement>getAllOxygenMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return oxygenMeasurementDAO.getAllMeasurements(citizen,startDate,endDate);
    }

    public List<TempMeasurement>getAllTemperatureMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate) throws SQLException {
        return temperatureMeasurementDAO.getAllMeasurements(citizen,startDate,endDate);
    }

    public void addBPMeasurement(Citizen citizen,float measurement) throws SQLException {
        bloodPressureMeasurementDAO.newMeasurements(citizen,measurement);
    }

    public void addBSMeasurement(Citizen citizen,float measurement) throws SQLException {
        bloodSugarMeasurementDAO.newMeasurement(citizen,measurement);
    }

    public void addOxygenMeasurement(Citizen citizen,int measurement) throws SQLException {
        oxygenMeasurementDAO.newMeasurement(citizen,measurement);
    }

    public void addTemperature(Citizen citizen,float measurement) throws SQLException {
        bloodPressureMeasurementDAO.newMeasurements(citizen,measurement);
    }
}
