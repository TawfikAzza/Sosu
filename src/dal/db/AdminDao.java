package dal.db;

import be.Admin;
import be.School;
import be.Teacher;
import bll.exceptions.UserException;
import dal.ConnectionManager;
import dal.DBCPDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    //private final ConnectionManager connectionManager;
    private  UsersDAO usersDAO;
    private DBCPDataSource dataSource;


    public AdminDao() throws IOException {
        //connectionManager = new ConnectionManager();
        dataSource=DBCPDataSource.getInstance();
        usersDAO= new UsersDAO();
    }

    public List<Admin>getAllAdmins()throws SQLException {
        List<Admin>allAdmins = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            String sql = "SELECT * FROM UserRoles WHERE roleName=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"Admin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int roleId= resultSet.getInt("roleID");
                String sql0 = "SELECT * FROM [user] WHERE roleID= ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql0);
                preparedStatement1.setInt(1,roleId);

                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    Admin admin =new Admin(resultSet1.getInt("id"),
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("user_name"),
                            resultSet1.getString("password"),
                            resultSet1.getString("e_mail"),
                            resultSet1.getInt("phone_number"));
                    admin.setSchoolId(resultSet1.getInt("school_id"));
                    admin.setSchoolName(getSchoolName(admin.getSchoolId()));
                    allAdmins.add(admin);
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
            try (Connection connection = dataSource.getConnection()) {
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
                        admin.setSchoolName(school.getName());
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
    public void deleteAdmin(Admin admin)throws SQLException{
    try (Connection connection = dataSource.getConnection()) {
        String sql = "DELETE FROM [user] WHERE id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, admin.getId());
        preparedStatement.executeUpdate();
    }
}
    public void editAdmin(Admin admin,School school) throws SQLException, UserException {
        boolean creation=false;
        try {
            exceptionCreation(admin.getFirstName(), admin.getLastName(), admin.getUserName(), admin.getPassWord(), admin.getEmail(), String.valueOf(admin.getPhoneNumber()),creation);
            try (Connection connection = dataSource.getConnection()) {
                String sql = "UPDATE [user] SET school_id=?, first_name =?, last_name = ?, user_name=?, password=?, e_mail=?, phone_number=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, school.getId());
                preparedStatement.setString(2, admin.getFirstName());
                preparedStatement.setString(3, admin.getLastName());
                preparedStatement.setString(4, admin.getUserName());
                preparedStatement.setString(5, admin.getPassWord());
                preparedStatement.setString(6, admin.getEmail());
                preparedStatement.setInt(7, admin.getPhoneNumber());
                preparedStatement.setInt(8, admin.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException sqlException){
            throw new UserException("Something went wrong in the database",new Exception());
        } catch (UserException e) {
            e.printStackTrace();
        }
    }
    private String getSchoolName(int schoolId)throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            String sql= "SELECT * FROM school WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,schoolId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("name");
            }
        }
        return null;
    }
}
