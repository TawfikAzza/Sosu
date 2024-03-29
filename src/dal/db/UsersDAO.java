package dal.db;


import be.*;
import bll.util.GlobalVariables;
import dal.ConnectionManager;
import dal.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    //private  ConnectionManager cm;
    private DBCPDataSource dataSource;

    private SchoolDAO schoolDAO;

    public UsersDAO() throws IOException {
        //cm = new ConnectionManager();
        dataSource=DBCPDataSource.getInstance();
    }

    public User compareLogins(String userName, String passWord){
        User user=null;
        try (Connection con = dataSource.getConnection()){
            String sql = "SELECT [user_name],[password],[e_mail], [roleID], [id],[school_id] FROM [user] WHERE [user_name] =? AND [password] =?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, passWord);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                int roleID = rs.getInt("roleID");
                System.out.println("RoleID "+roleID);

                if(roleID==1){
                    user = new Admin(id,"Admin",
                            "admin",rs.getString("user_name"),
                            rs.getString("password"),
                            rs.getString("e_mail"),25478963);
                    GlobalVariables.setCurrentAdmin((Admin)user);
                }
                if(roleID==2){
                    user = new Teacher(id,"Teacher",
                            "teacher",rs.getString("user_name"),
                            rs.getString("password"),
                            rs.getString("e_mail"),
                            25478963,
                             rs.getInt("school_id"));
                    GlobalVariables.setCurrentTeacher((Teacher)user);
                }
                if(roleID==3){
                    user = new Student(id,"Student",
                            "student",rs.getString("user_name"),
                            rs.getString("password"),
                            rs.getString("e_mail"),25478963);
                    GlobalVariables.setCurrentStudent((Student) user);
                }
                assert user != null;
                user.setRoleID(roleID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public int userNameTaken(String userName) throws SQLException {
        int counter = 0;
        try (Connection connection = dataSource.getConnection()){
            String sql= "SELECT * FROM [user] WHERE user_name= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                counter+=1;
            }
        }
        return counter;
    }

}

