package dal.db;

import be.Admin;
import be.School;
import be.Teacher;
import bll.exceptions.UserException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private final ConnectionManager connectionManager;
    private  UsersDAO usersDAO;

    public AdminDao() throws IOException {
        connectionManager = new ConnectionManager();
        usersDAO= new UsersDAO();
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

    public Admin newAdmin(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException{
        Admin admin = null;
        boolean creation = true;
        try {
            exceptionCreation(firstName,lastName,userName,passWord,email,phoneNumber, creation);
            if (school==null){
                throw new UserException("Please find a school for the Admin",new Exception());
            }
            try (Connection connection = connectionManager.getConnection()) {
                String sql0 = "SELECT * FROM UserRoles WHERE roleName=?";
                PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
                preparedStatement0.setString(1, "Admin");
                ResultSet resultSet0 = preparedStatement0.executeQuery();
                if (resultSet0.next()) {
                    int roleId = resultSet0.getInt("roleID");
                    String sql1 = "INSERT INTO  [user] VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement1.setInt(1, school.getId());
                    preparedStatement1.setString(2, firstName);
                    preparedStatement1.setString(3, lastName);
                    preparedStatement1.setString(4, userName);
                    preparedStatement1.setString(5, passWord);
                    preparedStatement1.setString(6, email);
                    preparedStatement1.setInt(7, Integer.parseInt(phoneNumber));
                    preparedStatement1.setInt(8, roleId);

                    preparedStatement1.executeUpdate();
                    ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
                    while (resultSet1.next()) {
                        int id = resultSet1.getInt(1);
                        admin = new Admin(id, firstName, lastName, userName, passWord, email, Integer.parseInt(phoneNumber));
                    }
                }
            }
        }catch (SQLException sqlException){
            throw new UserException("Something went wrong in the database",new Exception());
        }

        return admin;
    }

    private void exceptionCreation(String firstName, String lastName, String userName, String passWord, String email,String phoneNumber,Boolean creation) throws UserException, SQLException {
        UserException ue = new UserException();
        ue.checkUserFN(firstName);
        ue.checkUserLN(lastName);
        ue.checkUserUN(userName);

        if (creation)
            ue.checkUserUName(userName, usersDAO.userNameTaken(userName));
        ue.checkUserPassword(passWord);
        ue.checkEmail(email);
        ue.checkPhoneNumber(phoneNumber);

    }
}
