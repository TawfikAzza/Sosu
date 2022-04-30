package dal.db;

import be.School;
import be.Student;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private final ConnectionManager connectionManager;

    public StudentDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<Student> getAllStudents(String initials) throws SQLException {
        List<Student> allStudents = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql0 = "SELECT * FROM UserRoles WHERE roleName=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1, "Student");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("roleID");
                String sql1 = "SELECT * FROM [user] WHERE first_name LIKE ? OR last_name LIKE ? OR user_name LIKE ? OR password LIKE ? OR e_mail LIKE ? OR phone_number LIKE ? AND roleID=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                for (int i = 1; i <= 5; i++)
                    preparedStatement1.setString(i, "%"+initials+"%");
                try {
                    preparedStatement1.setInt(6, Integer.parseInt(initials));
                } catch (NumberFormatException numberFormatException) {
                    preparedStatement1.setInt(6, 0);
                }
                preparedStatement1.setInt(7, id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    Student student = new Student(resultSet1.getInt("id"),
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("user_name"),
                            resultSet1.getString("password"),
                            resultSet1.getString("e_mail"),
                            resultSet1.getInt("phone_number"));
                    student.setSchoolId(resultSet1.getInt("school_id"));
                    student.setSchoolName(schoolName(student.getSchoolId()));

                    allStudents.add(student);
                }
            }
        }
        return allStudents;
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws SQLException {
        Student student = null;
        try (Connection connection = connectionManager.getConnection()) {
            String sql0 = "SELECT * FROM UserRoles WHERE roleName=?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1, "Student");
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
                    student = new Student(id, firstName, lastName, userName, passWord, email, phoneNumber);
                    student.setSchoolId(school.getId());
                    student.setSchoolName(school.getName());
                }
            }
        }
        return student;
    }

    public void deleteStudent(Student student) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "DELETE FROM [user] WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void editStudent(Student student) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "UPDATE [user] SET first_name =?, last_name = ?, user_name=?, password=?, e_mail=?, phone_number=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getUserName());
            preparedStatement.setString(4, student.getPassWord());
            preparedStatement.setString(5, student.getEmail());
            preparedStatement.setInt(6, student.getPhoneNumber());
            preparedStatement.setInt(7, student.getId());
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
}
