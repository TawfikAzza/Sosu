package dal.db.measurementDAO;

import be.BloodPressureMeasurement;
import be.Citizen;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BloodPressureMeasurementDAO {

    ConnectionManager cm;
    public BloodPressureMeasurementDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<BloodPressureMeasurement>getAllMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate)throws SQLException {
        List<BloodPressureMeasurement>allMeasurements= new ArrayList<>();
        try (Connection connection =cm.getConnection()){
            String sql ="SELECT * FROM BloodPressure WHERE citizen_id=? AND date_observation BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setDate(2, Date.valueOf(startDate));
            preparedStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allMeasurements.add(new BloodPressureMeasurement(resultSet.getInt("citizen_id"),
                        resultSet.getFloat("measurement"),
                        resultSet.getDate("date_observation")));
            }
        }
        return allMeasurements;
    }
    public void newMeasurements(Citizen citizen,float bloodPressure)throws SQLException{
        try (Connection connection = cm.getConnection()){
            String sql= "INSERT INTO BloodPressure VALUES (?,?,getDate())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setFloat(2,bloodPressure);
            preparedStatement.executeUpdate();
        }
    }
}
