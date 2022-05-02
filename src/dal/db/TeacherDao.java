package dal.db;

import be.School;
import be.Teacher;
import bll.util.CheckInput;
import bll.exceptions.UserException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {

    private final ConnectionManager connectionManager;

    public TeacherDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<Teacher> getAllTeachers(String initials) throws SQLException {
        List<Teacher> allTeachers = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql0 = "SELECT * FROM UserRoles WHERE roleName=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1, "Teacher");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("roleID");
                String sql1 = "SELECT * FROM [user] WHERE (first_name=? OR last_name=? OR user_name= ? OR password=? OR e_mail=? OR phone_number=?) AND roleID=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                for (int i = 1; i <= 5; i++)
                    preparedStatement1.setString(i, initials);
                try {
                    preparedStatement1.setInt(6, Integer.parseInt(initials));
                } catch (NumberFormatException numberFormatException) {
                    preparedStatement1.setInt(6, 0);
                }
                preparedStatement1.setInt(7, id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    Teacher teacher = new Teacher(resultSet1.getInt("id"),
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("user_name"),
                            resultSet1.getString("password"),
                            resultSet1.getString("e_mail"),
                            resultSet1.getInt("phone_number"));
                    teacher.setSchoolId(resultSet1.getInt("school_id"));
                    teacher.setSchoolName(schoolName(teacher.getSchoolId()));

                    allTeachers.add(teacher);
                }
            }
        }
        return allTeachers;
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Teacher teacher = null;
        boolean creation = true;
        try {
            exceptionCreation(firstName,lastName,userName,passWord,email,phoneNumber, creation);
            if (school==null){
                throw new UserException("Please find a school for the teacher",new Exception());
            }
            try (Connection connection = connectionManager.getConnection()) {
                String sql0 = "SELECT * FROM UserRoles WHERE roleName=?";
                PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
                preparedStatement0.setString(1, "Teacher");
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
                        teacher = new Teacher(id, firstName, lastName, userName, passWord, email, Integer.parseInt(phoneNumber));
                        teacher.setSchoolId(school.getId());
                        teacher.setSchoolName(school.getName());
                    }
                }
            }
        }catch (SQLException sqlException){
            throw new UserException("Something went wrong in the database",new Exception());
        }

        return teacher;
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "DELETE FROM [user] WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void editTeacher(Teacher teacher,School school) throws UserException {
        boolean creation=false;
        try {
            exceptionCreation(teacher.getFirstName(), teacher.getLastName(), teacher.getUserName(), teacher.getPassWord(), teacher.getEmail(), String.valueOf(teacher.getPhoneNumber()),creation);
            try (Connection connection = connectionManager.getConnection()) {
                String sql = "UPDATE [user] SET school_id=?, first_name =?, last_name = ?, user_name=?, password=?, e_mail=?, phone_number=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, school.getId());
                preparedStatement.setString(2, teacher.getFirstName());
                preparedStatement.setString(3, teacher.getLastName());
                preparedStatement.setString(4, teacher.getUserName());
                preparedStatement.setString(5, teacher.getPassWord());
                preparedStatement.setString(6, teacher.getEmail());
                preparedStatement.setInt(7, teacher.getPhoneNumber());
                preparedStatement.setInt(8, teacher.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException sqlException){
            throw new UserException("Something went wrong in the database",new Exception());
        }

    }

    private String schoolName(int schoolId) throws SQLException {
        String schoolName = null;
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM school WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, schoolId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                schoolName = resultSet.getString("name");
        }
        return schoolName;
    }

    /* do not need this right now // fewer queries to database.
    private int schoolId(String schoolName) throws SQLException{
        int schoolId = 0;
        try (Connection connection = connectionManager.getConnection()){
            String sql = "SELECT * FROM school WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,schoolName);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next())
                schoolId = resultSet.getInt("id");
        }
        return schoolId;
    }*/
    private void exceptionCreation(String firstName, String lastName, String userName, String passWord, String email,String phoneNumber,Boolean creation) throws UserException, SQLException {
        if (firstName.isEmpty())
            throw new UserException("Please enter your first name.", new Exception());

        if (!(firstName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            UserException userException = new UserException("Please find a valid first name", new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;
        }
        if (lastName.isEmpty())
            throw new UserException("Please enter your last name.", new Exception());

        if (!(lastName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            UserException userException = new UserException("Please find a valid last name", new Exception());
            userException.setInstructions("A correct name is only composed of Alphabet characters");
            throw userException;
        }
        if (userName.isEmpty())
            throw new UserException("Please find a username.", new Exception());

        if (creation){
            if (userNameTaken(userName)>0){
                    UserException userException = new UserException("user name already exists.", new Exception());
                    userException.setInstructions("Please find another one and try again.");
                    throw userException;
        }else {
                if (userNameTaken(userName)>1){
                    UserException userException = new UserException("user name already exists.", new Exception());
                    userException.setInstructions("Please find another one and try again.");
                    throw userException;
                }
            }
        }

        if (CheckInput.isPasswordValid(passWord)) {
            UserException userException = new UserException("Please find a correct password.", new Exception());
            userException.setInstructions("A password is composed of an 9-length string containing only characters and digits, at least two of the digits");
            throw userException;
        }
        if (email.isEmpty())
            throw new UserException("Please enter your email.", new Exception());

        if (!CheckInput.isValidEmailAddress(email))
            throw new UserException("Please enter a valid email.", new Exception());
        try {
            Integer.parseInt(phoneNumber);
        }catch (NumberFormatException numberFormatException){
            UserException userException =new  UserException("Please enter a valid number",new Exception());
            userException.setInstructions("A valid number is composed of 8 digits.");
            throw userException;
        }
        if (phoneNumber.length()!=8){
            UserException userException =new  UserException("Please enter a valid number",new Exception());
            userException.setInstructions("A valid number is composed of 8 digits.");
            throw userException;
        }
    }

    private int userNameTaken(String userName) throws SQLException {
        int counter = 0;
        try (Connection connection = connectionManager.getConnection()){
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
