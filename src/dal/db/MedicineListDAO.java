package dal.db;

import be.Citizen;
import be.MedicineList;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.SQLException;

public class MedicineListDAO {


    private final ConnectionManager connectionManager;

    public MedicineListDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }



    public static void getMedicineList(MedicineList medicineList, Citizen citizen) throws SQLException {

    }


    public void addMedicineList(MedicineList medicineList) throws SQLException {

    }


    public void updateMedicineList(MedicineList medicineList) throws SQLException {

    }


}
