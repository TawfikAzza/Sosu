package dal.db;

import be.Citizen;


import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


public class CitizenDAO {
    private ConnectionManager cm;
    public CitizenDAO() throws IOException {
        cm = new ConnectionManager();
    }
    public Citizen getCitizen(int id) throws SQLException {
        Citizen citizen =null;
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE id = ?";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);
            pstsmt.setInt(1, id);
            ResultSet rs = pstsmt.executeQuery();
            if (rs.next()) {
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String address = rs.getString("address");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                int phoneNumber = rs.getInt("phoneNumber");
                boolean isTemplate = rs.getBoolean("isTemplate");
                int schoolID = rs.getInt("school_id");
                String cprNumber = rs.getString("cprNumber");

                citizen = new Citizen(id,fName,lName,cprNumber,address,phoneNumber,birthDate,isTemplate,schoolID);
            }
        }
        return citizen;
    }


    public Citizen editCitizen(Citizen citizenToEdit, Citizen newCitizen) throws SQLException {
        try(Connection connection = cm.getConnection()){
            String sql = "UPDATE Citizen\n" +
                    "SET \n" +
                    "\tfname = ?,\n" +
                    "\tlname = ?,\n" +
                    "\t[address] = ?,\n" +
                    "\tbirthDate = ?,\n" +
                    "\tphoneNumber = ?,\n" +
                    "\tisTemplate = ?,\n" +
                    "\tcprNumber = ?\n" +
                    "WHERE\n" +
                    "\tCitizen.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newCitizen.getFName());
            preparedStatement.setString(2,newCitizen.getLName());
            preparedStatement.setString(3, newCitizen.getAddress());
            preparedStatement.setDate(4, Date.valueOf(newCitizen.getBirthDate()));
            preparedStatement.setInt(5,newCitizen.getPhoneNumber());
            preparedStatement.setBoolean(6, newCitizen.isTemplate());
            preparedStatement.setString(7,newCitizen.getCprNumber());

            preparedStatement.setInt(8,citizenToEdit.getId());
            preparedStatement.executeUpdate();
        }
        return getCitizen(citizenToEdit.getId());

    }

    public void deleteCitizen(Citizen selectedCitizen) throws SQLException {
        try(Connection connection = cm.getConnection()){
            String sqlStatement = "DELETE FROM Citizen\n" +
                                    "WHERE Citizen.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1,selectedCitizen.getId());
            preparedStatement.executeUpdate();
        }
    }
}
