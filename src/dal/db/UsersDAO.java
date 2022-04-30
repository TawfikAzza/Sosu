package dal.db;


import be.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    ConnectionManager cm;

    public UsersDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public User compareLogins (String userName , String passWord) throws IOException {

        User user = null;
        try (Connection con = cm.getConnection()){
            String sql = "SELECT [user_name],[password],[e_mail], [roleID], [id] FROM user WHERE [user_name] =? ,[password] =?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, passWord);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                 user = new User(
                         rs.getInt("id"),
                         rs.getString("userName"),
                         rs.getString("passWord"),
                         rs.getString("email"),
                         rs.getInt("roleID")

                 );
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }

}

