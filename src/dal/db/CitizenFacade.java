package dal.db;

import be.*;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        }
    }

    private void addHealthConditions(Citizen citizen) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Condition> conditions = citizen.getHealthConditions();

            for(Condition con: conditions)
            {
                int catID = con.getCategoryID();
                int citizenID = con.getCitizenID();
                String description = con.getDescription();
                int status = con.getStatus();
                String text = con.getFreeText();
                String goal = con.getGoal();


                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setString(3, description);
                ps.setInt(4, status);
                ps.setString(5, text);
                ps.setString(6, goal);

                ps.addBatch(sql);
            }

            ps.executeBatch();
        }
    }

    private void addFunctionalAbilities(Citizen citizen) throws SQLException {
        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Ability> abilities = citizen.getFunctionalAbilities();

            for(Ability ability: abilities)
            {
                int catID = ability.getCategoryID();
                int citizenID = ability.getCitizenID();
                int status = ability.getScore();
                //String text = ability.getCitizenText();

                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                //ps.setString(3, text);
                ps.setInt(4, status);

                ps.addBatch(sql);
            }
            ps.executeBatch();
        }
    }

    private void addGeneralInfo(Citizen citizen) throws SQLException {

        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Conditions VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<GeneralInfo> generalInfo = citizen.getGeneralInfo();

            for(GeneralInfo info: generalInfo)
            {
                int catID = info.getCategoryID();
                int citizenID = info.getCitizenID();
                String content = info.getContent();

                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setString(3, content);

                ps.addBatch(sql);
            }
            ps.executeBatch();
        }
    }

}
