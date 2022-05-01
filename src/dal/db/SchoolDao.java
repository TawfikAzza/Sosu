package dal.db;

import be.School;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDao {
    private final ConnectionManager connectionManager;

    public SchoolDao() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<School>getAllSchools()throws SQLException{
        List<School>allSchools= new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            String sql="SELECT * FROM school";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                allSchools.add(new School(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        }
        return allSchools;
    }

    public void deleteSchool(School school) throws SQLException{
        try (Connection connection = connectionManager.getConnection())
        {
            String sql = "DELETE FROM school WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,school.getId());
            preparedStatement.executeUpdate();
        }
    }
    public List<String>getAllTeachers(School school)throws SQLException{
        List<String>allTeachers = new ArrayList<>();
        int roleId=0;
        try (Connection connection = connectionManager.getConnection()){
            String sql0="SELECT * FROM UserRoles WHERE roleName= ?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1,"Teacher");
            ResultSet resultSet0= preparedStatement0.executeQuery();
            return selectUsers(school, allTeachers, roleId, connection, resultSet0);
        }
    }

    public List<String>getAllStudents(School school)throws SQLException{
        List<String>allStudents = new ArrayList<>();
        int roleId=0;
        try (Connection connection = connectionManager.getConnection()){
            String sql0="SELECT * FROM UserRoles WHERE roleName= ?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1,"Student");
            ResultSet resultSet0= preparedStatement0.executeQuery();
            return selectUsers(school, allStudents, roleId, connection, resultSet0);
        }
    }

    private List<String> selectUsers(School school, List<String> allStudents, int roleId, Connection connection, ResultSet resultSet0) throws SQLException {
        if (resultSet0.next())
            roleId = resultSet0.getInt("roleID");
        String sql = "SELECT * FROM [user] WHERE school_id= ? AND roleID= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,school.getId());
        preparedStatement.setInt(2,roleId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            allStudents.add(resultSet.getString("first_name")+" "+resultSet.getString("last_name"));
        return allStudents;
    }

    public List<String>getAllCitizens(School school)throws SQLException{
        List<String>allCitizens = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            String sql="SELECT * FROM Citizen WHERE school_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,school.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allCitizens.add(resultSet.getString("fname")+" "+resultSet.getString("lname"));
            }
        }
        return allCitizens;
    }

    public School newSchool(String schoolName)throws SchoolException{
        School school=null;
        try {
            checkSchoolName(schoolName);
            try (Connection connection = connectionManager.getConnection()){
                String sql = "INSERT INTO school VAlUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,schoolName);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    school = new School(resultSet.getInt(1),schoolName);
                }
            }
        }catch (SQLException sqlException){
            throw  new SchoolException("Something went wrong in the database",new Exception());
        }

        return school;
    }

    private void checkSchoolName(String schoolName) throws SchoolException {
        if (schoolName.isEmpty())
            throw new SchoolException("Please enter a school name.", new Exception());

        if (!(schoolName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))) {
            SchoolException schoolException = new SchoolException("Please find a valid school name", new Exception());
            schoolException.setInstructions("A valid name is only composed of Alphabet characters");
            throw schoolException;
    }
}
}
