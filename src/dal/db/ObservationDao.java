package dal.db;

import be.Citizen;
import be.Observation;
import be.ObservationType;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObservationDao {
    private final ConnectionManager connectionManager;

    public ObservationDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<Observation> getAllObservations(ObservationType observationType, Citizen citizen, LocalDate fDay, LocalDate lDay) throws SQLException {
        List<Observation> allObservations = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM ObservationType WHERE type=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, observationType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int typeId = resultSet.getInt("id");
                String sql1 = "SELECT * FROM Observation WHERE type_id= ? AND citizen_id= ? AND ([date] BETWEEN ? AND ?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1, typeId);
                preparedStatement1.setInt(2, citizen.getId());
                preparedStatement1.setDate(3, Date.valueOf(fDay));
                preparedStatement1.setDate(4, Date.valueOf(lDay));
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    allObservations.add(new Observation(resultSet1.getFloat("measurement"),
                            resultSet1.getDate("date")));
                }
            }
        }
        return allObservations;
    }

    public void newObservation(ObservationType observationType, Citizen citizen, float measurement) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM ObservationType WHERE type= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, observationType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int typeId = resultSet.getInt("id");
                String sql1 = "INSERT INTO Observation VALUES(?,?,?,getDate())";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1, citizen.getId());
                preparedStatement1.setInt(2, typeId);
                preparedStatement1.setFloat(3, measurement);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
            }
        }
    }
}