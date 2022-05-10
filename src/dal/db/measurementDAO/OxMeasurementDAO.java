package dal.db.measurementDAO;

import be.Citizen;
import be.OxMeasurement;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxMeasurementDAO {

    ConnectionManager cm;
    public OxMeasurementDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<OxMeasurement> getAllMeasurements(Citizen citizen, LocalDate startDate, LocalDate endDate)throws SQLException {
        List<OxMeasurement>allMeasurements= new ArrayList<>();
        try (Connection connection =cm.getConnection()){
            String sql ="SELECT * FROM Oxygen WHERE citizen_id=? AND date_observation BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setDate(2, Date.valueOf(startDate));
            preparedStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allMeasurements.add(new OxMeasurement(resultSet.getInt("citizen_id"),
                        resultSet.getInt("measurement"),
                        resultSet.getDate("date_observation")));
            }
        }
        return allMeasurements;
    }
    public void newMeasurement(Citizen citizen,int oxygenMeasurement)throws SQLException{
        try (Connection connection = cm.getConnection()){
            String sql= "INSERT INTO Oxygen VALUES (?,?,getDate())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizen.getId());
            preparedStatement.setFloat(2,oxygenMeasurement);
            preparedStatement.executeUpdate();
        }
    }
}
