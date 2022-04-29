package dal.db;

import be.Citizen;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CitizenFacade {

    private final ConnectionManager cm;

    public CitizenFacade() throws IOException {
        cm = new ConnectionManager();
    }

    private void addCitizen(Citizen citizen) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Citizen VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            String fname = citizen.getFname();
            String lname = citizen.getLname();
            String address = citizen.getAddress();
            int phoneNumber = citizen.getPhoneNumber();
            boolean template = citizen.isTemplate();


            ps.setString();
        }
    }

}
