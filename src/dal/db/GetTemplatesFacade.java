package dal.db;

import be.Ability;
import be.Citizen;
import be.Condition;
import be.GeneralInfo;
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


    private List<Citizen> getCitizens() throws CitizenException {
        List<Citizen> citizens = new ArrayList<>();

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE isTemplate = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);

            ps.setInt(1, 1);

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
        List<Condition> conditions = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM Conditions WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
                    int id = rs.getInt(1);
                    int catID = rs.getInt(2);
                    String description = rs.getString(4);
                    int status = rs.getInt(5);
                    String text = rs.getString(6);
                    String goal = rs.getString(7);

                    Condition condition = new Condition(id, catID, citizenID, description, status, text, goal);
                    conditions.add(condition);
                }
                cit.setHealthConditions(conditions);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve health conditions from DB", throwables);
        }
    }

    private void attachFunctionalAbilities(List<Citizen> citizens) throws CitizenException {
        List<Ability> abilities = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM Abilities WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
                    int id = rs.getInt(1);
                    int catID = rs.getInt(2);
                    int score = rs.getInt(4);
                    int status = rs.getInt(5);
                    //String goals = rs.getString(6);

                    Ability ability = new Ability(id, catID, citizenID, score, status);
                    abilities.add(ability);
                }
                cit.setFunctionalAbilities(abilities);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
    }

    private void attachGeneralInfo(List<Citizen> citizens) throws CitizenException {
        List<GeneralInfo> info = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            for(Citizen cit : citizens)
            {
                String sqlSelect = "SELECT * FROM CitizenInfo WHERE citizenID = ?";
                PreparedStatement ps = connection.prepareStatement(sqlSelect);

                int citizenID = cit.getId();
                ps.setInt(1, citizenID);

                ResultSet rs = ps.executeQuery();

                while(rs.next())
                {
                    int id = rs.getInt(1);
                    int catID = rs.getInt(3);
                    String infoContent = rs.getString(4);

                    GeneralInfo content = new GeneralInfo(id, catID, citizenID, infoContent);
                    info.add(content);
                }
                cit.setGeneralInfo(info);
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Could not retrieve functional abilities from DB", throwables);
        }
    }

    public List<Citizen> retrieveTemplates() throws CitizenException {
        List<Citizen> citizens = getCitizens();
        attachHealthConditions(citizens);
        attachFunctionalAbilities(citizens);
        attachGeneralInfo(citizens);

        return citizens;
    }

}
