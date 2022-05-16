package dal.db;

import be.*;


import bll.exceptions.CitizenException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CitizenDAO {
    private ConnectionManager cm;

    public CitizenDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public Citizen getCitizen(int id) throws SQLException {
        Citizen citizen = null;
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE id = ?";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);
            pstsmt.setInt(1, id);
            ResultSet rs = pstsmt.executeQuery();
            if (rs.next()) {
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String address = rs.getString("address");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                int phoneNumber = rs.getInt("phoneNumber");
                boolean isTemplate = rs.getBoolean("isTemplate");
                int schoolID = rs.getInt("school_id");
                String cprNumber = rs.getString("cprNumber");

                citizen = new Citizen(id, fName, lName, address, phoneNumber, birthDate, isTemplate, schoolID);
            }
        }
        return citizen;
    }


    public Citizen editCitizen(Citizen citizenToEdit) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "UPDATE Citizen \n" +
                    "Set \n" +
                    "fname = ?,\n" +
                    "lname = ?,\n" +
                    "[address] = ?,\n" +
                    "birthDate = ?,\n" +
                    "phoneNumber = ?\n" +
                    "WHERE Citizen.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, citizenToEdit.getFName());
            preparedStatement.setString(2, citizenToEdit.getLName());
            preparedStatement.setString(3, citizenToEdit.getAddress());
            preparedStatement.setDate(4, Date.valueOf(citizenToEdit.getBirthDate()));
            preparedStatement.setInt(5, citizenToEdit.getPhoneNumber());
            preparedStatement.setInt(6, citizenToEdit.getId());

            preparedStatement.executeUpdate();
        }
        return citizenToEdit;
    }

    public void deleteCitizen(Citizen selectedCitizen) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlStatement = "DELETE FROM Citizen WHERE Citizen.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, selectedCitizen.getId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Citizen> getCitizens(int currentSchoolID, boolean isTemplate) throws CitizenException {
        List<Citizen> citizens = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM Citizen WHERE isTemplate = ? AND school_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBoolean(1, isTemplate);
            ps.setInt(2, currentSchoolID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String address = rs.getString(4);
                Date bday = rs.getDate(5);
                LocalDate birthdayConverted = bday.toLocalDate();
                int phoneNumber = rs.getInt(6);
                int schoolID = rs.getInt(8);

                Citizen citizen = new Citizen(id, fname, lname, address, phoneNumber, birthdayConverted, false, schoolID);
                citizens.add(citizen);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve citizens from DB", throwables);
        }
        return citizens;
    }

    private void addStudentCitizenRelation(Citizen citizen, Student student) throws CitizenException {
        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO CitizenStudentRelation VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            int citizenID = citizen.getId();
            int studentID = student.getId();

            ps.setInt(1, citizenID);
            ps.setInt(2, studentID);

            ps.execute();

        } catch (SQLException throwables) {
            throw new CitizenException("Error connecting to DB", throwables);
        }
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        for (Student stud : students) {
            addStudentCitizenRelation(template, stud);
        }
    }

    public void removeRelation(Student student, Citizen toRemove) throws CitizenException {
        try (Connection connection = cm.getConnection()) {

            String sql = "DELETE FROM CitizenStudentRelation WHERE citizenID = ? AND studentID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int citID = toRemove.getId();
            int studID = student.getId();

            ps.setInt(1, citID);
            ps.setInt(2, studID);

            ps.execute();
        } catch (SQLException throwables) {
            throw new CitizenException("Error removing relation", throwables);
        }
    }
}
