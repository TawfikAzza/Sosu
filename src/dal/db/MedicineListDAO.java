package dal.db;

import be.Citizen;
import be.MedicineList;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineListDAO {


    private final ConnectionManager connectionManager;

    public MedicineListDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }



    public MedicineList getMedicineList(Citizen citizen) throws SQLException {
        MedicineList medicineListSearched = null;

        try (Connection con = connectionManager.getConnection()){
            String sql = "SELECT * FROM MedicineList WHERE citizenID = ? " ;

            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, citizen.getId());

            ResultSet rst = prst.executeQuery();
            while (rst.next()){
                medicineListSearched = new MedicineList(rst.getInt("id"),
                        rst.getInt("citizenID"),
                        rst.getString("content"));
            }

        }
       return medicineListSearched;

    }


    public void addMedicineList(MedicineList medicineList) throws SQLException {
        try (Connection con = connectionManager.getConnection()){

            String sql = "INSERT INTO MedicineList VALUES (?,?) ";
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, medicineList.getId());
        }

    }


    public void updateMedicineList(MedicineList medicineList) throws SQLException {

    }


}

/*
 public void addAbility(Ability ability) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlInsert = "INSERT INTO Abilities VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setInt(1,ability.getCategoryID());
            pstmt.setInt(2,ability.getCitizenID());
            pstmt.setInt(3,ability.getScore());
            pstmt.setInt(4,ability.getStatus());
            pstmt.setString(5, ability.getGoals());
            pstmt.execute();
        }
    }
 */
