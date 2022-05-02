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

public class CitizenFacade {

    private final ConnectionManager cm;

    public CitizenFacade() throws IOException {
        cm = new ConnectionManager();
    }

    private Citizen addCitizen(Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Citizen VALUES (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String fname = citizen.getFName();
            String lname = citizen.getLName();
            String address = citizen.getAddress();
            LocalDate birthdate = citizen.getBirthDate();
            int phoneNumber = citizen.getPhoneNumber();
            boolean template = citizen.isTemplate();
            String cprNumber = citizen.getCprNumber();

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, address);
            ps.setDate(4, Date.valueOf(birthdate));
            ps.setInt(5, phoneNumber);
            ps.setBoolean(6, template);

            ps.setInt(7,1);//The schoolID column, set to 1 by default for now
            //TODO change

            ps.setString(8,cprNumber);

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next())
                citizen.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new CitizenException("Error uploading citizen to DB", e);
        }
        return citizen;
    }

    private void addHealthConditions(Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Condition> conditions = citizen.getHealthConditions();

            for(Condition con: conditions)
            {
                int catID = con.getCategoryID();
                int citizenID = con.getCitizenID();
                String description = con.getDescription();
                int status = con.getStatus();
                String text = con.getFreeText();
                String goal = con.getGoal();


                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setString(3, description);
                ps.setInt(4, status);
                ps.setString(5, text);
                ps.setString(6, goal);

                ps.addBatch(sql);
            }

            ps.executeBatch();
        } catch (SQLException throwables) {
            throw new CitizenException("Error uploading health conditions", throwables);
        }
    }

    private void addFunctionalAbilities(Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Ability> abilities = citizen.getFunctionalAbilities();

            for(Ability ability: abilities)
            {
                int catID = ability.getCategoryID();
                int citizenID = ability.getCitizenID();
                int status = ability.getScore();
                //String text = ability.getCitizenText();

                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                //ps.setString(3, text);
                ps.setInt(4, status);

                ps.addBatch(sql);
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throw new CitizenException("Error uploading functional abilities", throwables);
        }
    }

    private void addGeneralInfo(Citizen citizen) throws CitizenException {

        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Conditions VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<GeneralInfo> generalInfo = citizen.getGeneralInfo();

            for(GeneralInfo info: generalInfo)
            {
                int catID = info.getCategoryID();
                int citizenID = info.getCitizenID();
                String content = info.getContent();

                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setString(3, content);

                ps.addBatch(sql);
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throw new CitizenException("Error uploading general info", throwables);
        }
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


    public Citizen addCitizenToDB(Citizen citizen) throws CitizenException {
        Citizen createdCitizen = addCitizen(citizen);
        addFunctionalAbilities(createdCitizen);
        addGeneralInfo(createdCitizen);
        addHealthConditions(createdCitizen);

        return createdCitizen;
    }

    public void copyCitizenToDB(Citizen citizen, ArrayList<Student> students) throws CitizenException {
        Citizen added = addCitizenToDB(citizen);
        for(Student stud : students)
        {
            addStudentCitizenRelation(added, stud);
        }
    }

}
