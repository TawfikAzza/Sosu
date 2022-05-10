package dal.db.measurementDAO;

import be.BloodSugarMeasurement;
import be.Citizen;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BloodSugarMeasurementDAO {

    ConnectionManager cm;
    public BloodSugarMeasurementDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<BloodSugarMeasurement>getAllMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate)throws SQLException {
        List<BloodSugarMeasurement>allMeasurements= new ArrayList<>();
        try (Connection connection =cm.getConnection()){
            String sql ="SELECT * FROM BloodSugar WHERE citizen_id=? AND date_observation BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setDate(2, Date.valueOf(startDate));
            preparedStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allMeasurements.add(new BloodSugarMeasurement(resultSet.getInt("citizen_id"),
                        resultSet.getFloat("measurement"),
                        resultSet.getDate("date_observation")));
            }
        }
        return allMeasurements;
    }
    public void newMeasurement(Citizen citizen,float bloodSugar)throws SQLException{
        try (Connection connection = cm.getConnection()){
            String sql= "INSERT INTO BloodSugar VALUES (?,?,getDate())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setFloat(2,bloodSugar);
            preparedStatement.executeUpdate();
        }
    }
}
