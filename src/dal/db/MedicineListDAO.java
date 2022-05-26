package dal.db;

import be.Citizen;
import be.MedicineList;
import dal.ConnectionManager;
import dal.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineListDAO {


    //private final ConnectionManager connectionManager;
    private DBCPDataSource dataSource;


    public MedicineListDAO() throws IOException {
        //connectionManager = new ConnectionManager();
        dataSource=DBCPDataSource.getInstance();
    }


    public MedicineList getMedicineList(Citizen citizen) throws SQLException {
        MedicineList medicineListSearched = null;

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM MedicineList WHERE citizenID = ? ";

            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, citizen.getId());

            ResultSet rst = prst.executeQuery();
            while (rst.next()) {
                medicineListSearched = new MedicineList(rst.getInt("id"),
                        rst.getInt("citizenID"),
                        rst.getString("content"));
            }

        }
        return medicineListSearched;

    }


    public void addMedicineList(MedicineList medicineList) throws SQLException {
        try (Connection con = dataSource.getConnection()) {

            String sql = "INSERT INTO MedicineList VALUES (?,?) ";
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, medicineList.getCitizenID());
            prst.setString(2, medicineList.getMedicineList());
            prst.execute();
        }

    }


    public void updateMedicineList(MedicineList medicineList) throws SQLException {

        try (Connection con = dataSource.getConnection()) {

            String sql = "UPDATE MedicineList  set content =? WHERE citizenID =? ";
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, medicineList.getMedicineList());
            prst.setInt(2, medicineList.getCitizenID());
            prst.execute();

        }


    }
}

