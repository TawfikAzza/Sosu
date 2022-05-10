package dal.db.measurementDAO;

import dal.ConnectionManager;

import java.io.IOException;

public class BMIMeasurementAO {
    ConnectionManager cm;
    public BMIMeasurementAO() throws IOException {
        cm = new ConnectionManager();
    }
}
