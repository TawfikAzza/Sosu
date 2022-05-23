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

    public List<Admin>getAllAdmins(String initials)throws SQLException {
        List<Admin>allAdmins = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            String sql = "SELECT * FROM UserRoles WHERE roleName=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"Admin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int roleId= resultSet.getInt("roleID");
                PreparedStatement preparedStatement1;
                if (initials.equals("*")){
                String sql0 = "SELECT * FROM [user] WHERE roleID= ?";
                preparedStatement1 = connection.prepareStatement(sql0);
                preparedStatement1.setInt(1,roleId);
                }else {
                    String sql0 = "SELECT * FROM [user] WHERE (first_name=? OR last_name=? OR user_name= ? OR password=? OR e_mail=? OR phone_number=?) AND roleID=?";
                    preparedStatement1 = connection.prepareStatement(sql0);
                    for (int i = 1; i <= 5; i++)
                        preparedStatement1.setString(i, initials);
                    try {
                        preparedStatement1.setInt(6, Integer.parseInt(initials));
                    } catch (NumberFormatException numberFormatException) {
                        preparedStatement1.setInt(6, 0);
                    }
                    preparedStatement1.setInt(7,roleId);
                }

                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    allAdmins.add( new Admin(resultSet1.getInt("id"),
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("user_name"),
                            resultSet1.getString("password"),
                            resultSet1.getString("e_mail"),
                            resultSet1.getInt("phone_number")));
                }
            }
        }
        return allAdmins;
    }

    public Admin newAdmin() {
        return null;
    }
}
