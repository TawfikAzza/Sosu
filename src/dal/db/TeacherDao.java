package dal.db;

import be.School;
import be.Teacher;
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
                String sql1 = "SELECT * FROM [user] WHERE first_name=? OR last_name=? OR user_name= ? OR password=? OR e_mail=? OR phone_number=? AND roleID=?";
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

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws SQLException {
        Teacher teacher = null;
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
                preparedStatement1.setInt(7, phoneNumber);
                preparedStatement1.setInt(8, roleId);

                preparedStatement1.executeUpdate();
                ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
                while (resultSet1.next()) {
                    int id = resultSet1.getInt(1);
                    teacher = new Teacher(id, firstName, lastName, userName, passWord, email, phoneNumber);
                    teacher.setSchoolId(school.getId());
                    teacher.setSchoolName(school.getName());
                }
            }
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

    public void editTeacher(Teacher teacher) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "UPDATE [user] SET first_name =?, last_name = ?, user_name=?, password=?, e_mail=?, phone_number=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getUserName());
            preparedStatement.setString(4, teacher.getPassWord());
            preparedStatement.setString(5, teacher.getEmail());
            preparedStatement.setInt(6, teacher.getPhoneNumber());
            preparedStatement.setInt(7, teacher.getId());
            preparedStatement.executeUpdate();
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
}
