package dal.db.measurementDAO;

import dal.ConnectionManager;

import java.io.IOException;

public class IBMDMeasurementAO {
    ConnectionManager cm;
    public IBMDMeasurementAO() throws IOException {
        cm = new ConnectionManager();
    }
}
