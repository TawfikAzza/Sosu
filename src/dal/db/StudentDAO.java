package dal.db;

import be.*;
import bll.util.CheckInput;
import bll.exceptions.UserException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import dal.DBCPDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

   // private final ConnectionManager connectionManager;
    UsersDAO usersDAO = new UsersDAO();
    private DBCPDataSource dataSource;


    public StudentDAO() throws IOException {
        //connectionManager = new ConnectionManager();
        dataSource=DBCPDataSource.getInstance();
    }

    public List<Student> getAllStudents(int schoolId) throws SQLException {
        List<Student> allStudents = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql0 = "SELECT * FROM UserRoles WHERE roleName=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1, "Student");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("roleID");
                String sql1= "SELECT * FROM [user] WHERE roleID=? AND school_id= ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,id);
                preparedStatement1.setInt(2,schoolId);

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

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Student student = null;
        boolean creation=true;
        try {
            exceptionCreation(firstName,lastName,userName,passWord,email,phoneNumber,creation);
            if (school==null){
                throw new UserException("Please find a school for the teacher",new Exception());
            }
            try (Connection connection = dataSource.getConnection()) {
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
                    preparedStatement1.setInt(7, Integer.parseInt(phoneNumber));
                    preparedStatement1.setInt(8, roleId);

                    preparedStatement1.executeUpdate();
                    ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
                    while (resultSet1.next()) {
                        int id = resultSet1.getInt(1);
                        student = new Student(id, firstName, lastName, userName, passWord, email, Integer.parseInt(phoneNumber));
                        student.setSchoolId(school.getId());
                        student.setSchoolName(school.getName());
                    }
                }
        }
        }catch (SQLException sqlException){
            throw new UserException("Something wrong went in the database",new Exception());
        }
        return student;
    }

    public void deleteStudent(Student student) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM [user] WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void editStudent(School school, Student student) throws  UserException {
        boolean creation=false;
        try {
            exceptionCreation(student.getFirstName(),student.getLastName(),student.getUserName(),student.getPassWord(),student.getEmail(),String.valueOf(student.getPhoneNumber()),creation);
            try (Connection connection = dataSource.getConnection()) {
                String sql = "UPDATE [user] SET school_id=?, first_name =?, last_name = ?, user_name=?, password=?, e_mail=?, phone_number=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, school.getId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.setString(4, student.getUserName());
                preparedStatement.setString(5, student.getPassWord());
                preparedStatement.setString(6, student.getEmail());
                preparedStatement.setInt(7, student.getPhoneNumber());
                preparedStatement.setInt(8, student.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException sqlException){
            throw new UserException("Something wrong went in the database",new Exception());
        }

    }

    private String schoolName(int schoolId) throws SQLException {
        String schoolName = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM school WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, schoolId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                schoolName = resultSet.getString("name");
        }
        return schoolName;
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



    public ArrayList<Student> getAllStudentsFromDB(School currentSchool) throws UserException {
        ArrayList<Student> students = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT [id],[first_name],[last_name] FROM [user] WHERE roleID = ? AND school_id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, 3);
            ps.setInt(2,currentSchool.getId());

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);


                Student student = new Student(id, firstName, lastName);
                students.add(student);

            }
        } catch (SQLException throwables) {
            throw new UserException("Could not connect to DB", throwables);
        }
        return students;
    }

    public Student getStudent(Student student)throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            String sql ="SELECT * FROM [user] WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,student.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student.setUserName(resultSet.getString("user_name"));
                student.setPassWord(resultSet.getString("password"));
                student.setEmail(resultSet.getString("e_mail"));
                student.setPhoneNumber(resultSet.getInt("phone_number"));
            }
        }
        return student;
    }
    }
