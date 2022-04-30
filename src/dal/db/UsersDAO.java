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
     static ConnectionManager cm;

    public  UsersDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public static User compareLogins(String userName, String passWord) throws IOException {

        User user = null;

        try (Connection con = cm.getConnection()){
            String sql = "SELECT [user_name],[password],[e_mail], [roleID], [id] FROM user WHERE [user_name] =? ,[password] =?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, passWord);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                int roleID = rs.getInt("roleID");

             //   user = new User(id, roleID);



            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}

