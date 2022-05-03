package dal.db;

import be.Citizen;

import bll.exceptions.CitizenException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CitizenDAO {
    private ConnectionManager cm;
    public CitizenDAO() throws IOException {
        cm = new ConnectionManager();
    }
    public Citizen getCitizen(int id) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String address = rs.getString(4);
                LocalDate birthday = rs.getDate(5).toLocalDate();
                int phoneNumber = rs.getInt(6);
                boolean isTemplate = rs.getBoolean(7);
                int schoolID = rs.getInt(8);
                String cpr = rs.getString(9);

                return new Citizen(id, firstName, lastName, cpr, address, phoneNumber, birthday, isTemplate, schoolID);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve citizen from DB", throwables);
        }
        return null;
    }


}
