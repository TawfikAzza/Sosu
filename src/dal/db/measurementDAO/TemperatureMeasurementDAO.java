package dal.db.measurementDAO;

import be.Citizen;
import be.TemperatureMeasurement;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TemperatureMeasurementDAO {
    ConnectionManager cm;
    public TemperatureMeasurementDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<TemperatureMeasurement> getAllMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate)throws SQLException {
        List<TemperatureMeasurement>allMeasurements= new ArrayList<>();
        try (Connection connection =cm.getConnection()){
            String sql ="SELECT * FROM Temperature WHERE citizen_id=? AND date_observation BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setDate(2, Date.valueOf(startDate));
            preparedStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allMeasurements.add(new TemperatureMeasurement(resultSet.getInt("citizen_id"),
                        resultSet.getFloat("measurement"),
                        resultSet.getDate("date_observation")));
            }
        }
        return allMeasurements;
    }
    public void newMeasurement(Citizen citizen,float temperature)throws SQLException{
        try (Connection connection = cm.getConnection()){
            String sql= "INSERT INTO Temperature VALUES (?,?,getDate())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setFloat(2,temperature);
            preparedStatement.executeUpdate();
        }
    }
}
