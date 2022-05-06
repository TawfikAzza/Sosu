package dal.db;

import be.*;
import bll.exceptions.CitizenException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetTemplatesFacade {

    private ConnectionManager cm;


    public GetTemplatesFacade() throws IOException {
        this.cm = new ConnectionManager();
    }


    private List<Citizen> getCitizens(Teacher currentTeacher) throws CitizenException {
        List<Citizen> citizens = new ArrayList<>();

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE isTemplate = ? AND school_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            ps.setInt(1, 1);
            ps.setInt(2,currentTeacher.getSchoolId());

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String address = rs.getString(4);
                Date bday = rs.getDate(5);
                LocalDate birthdayConverted = bday.toLocalDate();
                int phoneNumber = rs.getInt(6);
                int schoolID = rs.getInt(8);
                String cpr = rs.getString(9);


                Citizen citizen = new Citizen(id, fname, lname, cpr, address, phoneNumber, birthdayConverted, true, schoolID);
                citizens.add(citizen);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve templates from DB", throwables);
        }
        return citizens;
    }

    private void attachHealthConditions(List<Citizen> citizens) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM Conditions WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);
                List<Condition> conditions = new ArrayList<>();

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
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
                cit.setHealthConditions(conditions);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve health conditions from DB", throwables);
        }
    }

    private void attachFunctionalAbilities(List<Citizen> citizens) throws CitizenException {

        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM Abilities WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);
                List<Ability> abilities = new ArrayList<>();

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
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

                    Ability ability = new Ability(id, catID, citizenID, score, status);
                    ability.setGoals(goals);
                    ability.setPerformance(performance);
                    ability.setMeaning(meaning);
                    ability.setExpectedScore(expectedScore);
                    ability.setImportantNote(importantNote);
                    ability.setVisitDate(visitDate);
                    ability.setObservation(observations);
                    abilities.add(ability);
                }
                cit.setFunctionalAbilities(abilities);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
    }

    private void attachGeneralInfo(List<Citizen> citizens) throws CitizenException {
        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM CitizenInfo WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);
                List<GeneralInfo> info = new ArrayList<>();

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
                    int id = rs.getInt(1);
                    int catID = rs.getInt(3);
                    String infoContent = rs.getString(4);

                    GeneralInfo content = new GeneralInfo(id, citizenID, catID, infoContent);
                    info.add(content);
                }
                cit.setGeneralInfo(info);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
    }

    public List<Citizen> retrieveTemplates(Teacher currentTeacher) throws CitizenException {
        List<Citizen> citizens = getCitizens(currentTeacher);
        attachHealthConditions(citizens);
        attachFunctionalAbilities(citizens);
        attachGeneralInfo(citizens);

        return citizens;
    }

}
