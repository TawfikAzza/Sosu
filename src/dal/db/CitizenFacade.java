package dal.db;

import be.*;
import bll.exceptions.CitizenException;

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

    private Citizen addCitizen(Citizen citizen, boolean isTemplate) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Citizen VALUES (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String fname = citizen.getFName();
            String lname = citizen.getLName();
            String address = citizen.getAddress();
            LocalDate birthdate = citizen.getBirthDate();
            int phoneNumber = citizen.getPhoneNumber();
            String cprNumber = citizen.getCprNumber();
            int schoolID = citizen.getSchoolID();

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, address);
            ps.setDate(4, Date.valueOf(birthdate));
            ps.setInt(5, phoneNumber);
            ps.setBoolean(6, isTemplate);

            ps.setInt(7,schoolID);

            ps.setString(8,cprNumber);

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                if (isTemplate)
                citizen.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new CitizenException("Error uploading citizen to DB", e);
        }
        return citizen;
    }

    private void addHealthConditions(Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Condition> conditions = citizen.getHealthConditions();

            if (conditions!=null)
            for(Condition con: conditions)
            {
                int catID = con.getCategoryID();
                int citizenID = citizen.getId();
                String importantNote = con.getImportantNote();
                int status = con.getStatus();
                String assessment = con.getAssessement();
                String goal = con.getGoal();
                int expectedScore = con.getExpectedScore();
                Date visitDate = Date.valueOf(con.getVisitDate());
                String observations = con.getObservation();


                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setString(3, importantNote);
                ps.setInt(4, status);
                ps.setString(5, assessment);
                ps.setString(6, goal);
                ps.setInt(7, expectedScore);
                ps.setDate(8, visitDate);
                ps.setString(9, observations);

                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException throwables) {
            throw new CitizenException("Error uploading health conditions", throwables);
        }
    }

    private void addFunctionalAbilities(Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Abilities VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Ability> abilities = citizen.getFunctionalAbilities();

            if (abilities!=null)
            for(Ability ability: abilities)
            {
                int catID = ability.getCategoryID();
                int citizenID = citizen.getId();
                int score = ability.getScore();
                int status = ability.getStatus();
                String goals = ability.getGoals();
                int performance = ability.getPerformance();
                int meaning = ability.getMeaning();
                int expectedScore = ability.getExpectedScore();
                String importantNote = ability.getImportantNote();
                Date visitDate = Date.valueOf(ability.getVisitDate());
                String observation = ability.getObservation();

                ps.setInt(1, catID);
                ps.setInt(2, citizenID);
                ps.setInt(3, score);
                ps.setInt(4, status);
                ps.setString(5, goals);
                ps.setInt(6, performance);
                ps.setInt(7, meaning);
                ps.setInt(8, expectedScore);
                ps.setString(9, importantNote);
                ps.setDate(10, visitDate);
                ps.setString(11, observation);

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throw new CitizenException("Error uploading functional abilities", throwables);
        }
    }

    private void addGeneralInfo(Citizen citizen) throws CitizenException {

        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO CitizenInfo VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<GeneralInfo> generalInfo = citizen.getGeneralInfo();

            if (generalInfo!=null)
            for(GeneralInfo info: generalInfo)
            {
                int catID = info.getCategoryID();

                int citizenID = citizen.getId();

                String content = info.getContent();
                System.out.println(content);

                ps.setInt(1, citizenID);
                ps.setInt(2, catID);
                ps.setString(3, content);

                ps.addBatch();
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


    public Citizen addCitizenToDB(Citizen citizen, boolean isTemplate) throws CitizenException {
        Citizen createdCitizen = addCitizen(citizen, isTemplate);
        System.out.println("In citizen facade addCitizen: id:  "+createdCitizen.getId());
        addFunctionalAbilities(createdCitizen);
        addGeneralInfo(createdCitizen);
        addHealthConditions(createdCitizen);

        return createdCitizen;
    }

    public void copyCitizenToDB(Citizen template, ArrayList<Student> students) throws CitizenException {
        Citizen added = addCitizenToDB(template, false);
        for(Student stud : students)
        {
            addStudentCitizenRelation(added, stud);
        }
    }

}
