package dal.db;

import be.Citizen;
import be.MedicineList;
import dal.ConnectionManager;

import java.io.IOException;

public class MedicineListDAO {


    private final ConnectionManager connectionManager;

    public MedicineListDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }


}
