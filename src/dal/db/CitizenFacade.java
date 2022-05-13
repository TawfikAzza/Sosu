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

            int id = -1;
            String fname = citizen.getFName();
            String lname = citizen.getLName();
            String address = citizen.getAddress();
            LocalDate birthdate = citizen.getBirthDate();
            int phoneNumber = citizen.getPhoneNumber();
            int schoolID = citizen.getSchoolID();

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, address);
            ps.setDate(4, Date.valueOf(birthdate));
            ps.setInt(5, phoneNumber);
            ps.setBoolean(6, isTemplate);
            ps.setInt(7,schoolID);
            ps.setInt(8,1);

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            return new Citizen(id, fname, lname, address, phoneNumber, birthdate, isTemplate, schoolID);
        } catch (SQLException e) {
            throw new CitizenException("Error uploading citizen to DB", e);
        }
    }

    private List<Condition> getHealthConditions(Citizen citizen) throws CitizenException {
        List<Condition> conditions = new ArrayList<>();

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Conditions WHERE citizenID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            int citizenID = citizen.getId();
            ps.setInt(1, citizenID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int catID = rs.getInt(2);
                String importantNote = rs.getString(4);
                int status = rs.getInt(5);
                String assessment = rs.getString(6);
                String goal = rs.getString(7);
                int expectedScore = rs.getInt(8);
                LocalDate visitDate = rs.getDate(9).toLocalDate();
                String observations = rs.getString(10);

                Condition condition = new Condition(id, catID, citizenID, importantNote, status, assessment, goal);
                condition.setExpectedScore(expectedScore);
                condition.setVisitDate(visitDate);
                condition.setObservation(observations);
                conditions.add(condition);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve health conditions from DB", throwables);
        }
        return conditions;
    }

    private List<Ability> getFunctionalAbilities(Citizen citizen) throws CitizenException {
        List<Ability> abilities = new ArrayList<>();

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Abilities WHERE citizenID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            int citID = citizen.getId();
            ps.setInt(1, citID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int catID = rs.getInt(2);
                int score = rs.getInt(4);
                int status = rs.getInt(5);
                String goals = rs.getString(6);
                int performance = rs.getInt(7);
                int meaning = rs.getInt(8);
                int expectedScore = rs.getInt(9);
                String importantNote = rs.getString(10);
                LocalDate visitDate = rs.getDate(11).toLocalDate();
                String observations = rs.getString(12);

                Ability ability = new Ability(id, catID, citID, score, status);
                ability.setGoals(goals);
                ability.setPerformance(performance);
                ability.setMeaning(meaning);
                ability.setExpectedScore(expectedScore);
                ability.setImportantNote(importantNote);
                ability.setVisitDate(visitDate);
                ability.setObservation(observations);
                abilities.add(ability);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
        return abilities;
    }

    private List<GeneralInfo> getGeneralInfo(Citizen citizen) throws CitizenException {
        List<GeneralInfo> info = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM CitizenInfo WHERE citizenID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            int citizenID = citizen.getId();
            ps.setInt(1, citizenID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int catID = rs.getInt(3);
                String infoContent = rs.getString(4);

                GeneralInfo content = new GeneralInfo(id, citizenID, catID, infoContent);
                info.add(content);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
        return info;
    }

    private void addHealthConditions(List<Condition> conditions, Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Conditions VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

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

    private void addFunctionalAbilities(List<Ability> abilities, Citizen citizen) throws CitizenException {
        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO Abilities VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

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

    private void addGeneralInfo(List<GeneralInfo> generalInfo, Citizen citizen) throws CitizenException {

        try (Connection connection = cm.getConnection()) {

            String sql = "INSERT INTO CitizenInfo VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            if (generalInfo!=null)
                for(GeneralInfo info: generalInfo)
                {
                    int catID = info.getCategoryID();

                    int citizenID = citizen.getId();

                    String content = info.getContent();

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

    private void copyHealthConditions(Citizen template, Citizen copy) throws CitizenException {
        List<Condition> conditions = getHealthConditions(template);
        addHealthConditions(conditions, copy);
    }

    private void copyFunctionalAbilities(Citizen template, Citizen copy) throws CitizenException {
        List<Ability> abilities = getFunctionalAbilities(template);
        addFunctionalAbilities(abilities, copy);
    }

    private void copyGeneralInfo(Citizen template, Citizen copy) throws CitizenException {
        List<GeneralInfo> info = getGeneralInfo(template);
        addGeneralInfo(info, copy);
    }


    public Citizen copyCitizenToDb(Citizen citizen, boolean isTemplate) throws CitizenException {
        Citizen createdCitizen = addCitizen(citizen, isTemplate);
        copyHealthConditions(citizen, createdCitizen);
        copyFunctionalAbilities(citizen, createdCitizen);
        copyGeneralInfo(citizen, createdCitizen);

        return createdCitizen;
    }

    public List<Citizen> duplicateCitizen(Citizen citizen, int amount, boolean isTemplate) throws CitizenException {
        ArrayList<Citizen> duplicates = new ArrayList<>();
        for(int i=0; i<amount; i++)
        {
            duplicates.add(copyCitizenToDb(citizen, isTemplate));
        }
        return duplicates;
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
        for(Student stud : students)
        {
            addStudentCitizenRelation(template, stud);
        }
    }
}
