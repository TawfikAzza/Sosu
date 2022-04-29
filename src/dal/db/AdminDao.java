package dal.db;

import be.Admin;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private final ConnectionManager connectionManager;

    public AdminDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<Admin>getAllAdmins()throws SQLException {
        List<Admin>allAdmins = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            String sql = "SELECT * FROM UserRoles WHERE roleName=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"Admin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allAdmins.add( new Admin(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        resultSet.getString("e_mail"),
                        resultSet.getInt("phone_number")));
            }
        }
        return allAdmins;
    }
}
