package dal.db;

import be.Citizen;

import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CitizenDAO {
    private ConnectionManager cm;
    public CitizenDAO() throws IOException {
        cm = new ConnectionManager();
    }
    public Citizen getCitizen(int id) throws Exception {
        Citizen citizen =null;
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE id = ?";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);
            pstsmt.setInt(1, id);
            ResultSet rs = pstsmt.executeQuery();
            if (rs.next()) {

            }
        }
        return null;
    }
}
