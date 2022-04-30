package dal.db;

import be.School;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDao {
    private final ConnectionManager connectionManager;

    public SchoolDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<School>getAllSchools()throws SQLException{
        List<School>allSchools= new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            String sql="SELECT * FROM school";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                allSchools.add(new School(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        }
        return allSchools;
    }

    public void deleteSchool(School school) throws SQLException{
        try (Connection connection = connectionManager.getConnection())
        {
            String sql = "DELETE FROM school WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,school.getId());
            preparedStatement.executeUpdate();
        }
    }
}
