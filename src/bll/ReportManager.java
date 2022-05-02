package bll;

import be.*;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import dal.db.CitizenDAO;
import dal.db.FunctionalAbilityDAO;
import dal.db.HealthConditionDAO;
import gui.utils.DisplayMessage;
import javafx.util.Pair;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportManager {
    private HealthConditionDAO healthConditionDAO;
    private FunctionalAbilityDAO functionalAbilityDAO;
    private CitizenDAO citizenDAO;

    public ReportManager() throws HealthCategoryException {
        try {
            healthConditionDAO = new HealthConditionDAO();
            functionalAbilityDAO = new FunctionalAbilityDAO();
            citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            throw new HealthCategoryException("Error while trying to connect to the database",e);
        }
    }
    public CitizenReport getCitizenReport(int idCitizen) {
        List<Condition> conditionList = new ArrayList<>();
        return null;

    }

    public HashMap<Integer, List<Pair<AbilityCategory, Ability>>> getAbilitiesFromCitizen(Citizen citizen) throws CitizenReportException {
        try {
            return functionalAbilityDAO.getAbilitiesFromCitizen(citizen.getId());
        } catch (SQLException e) {
            throw new CitizenReportException("Error while retrieving Citizen's data report from the database",e);
        }
    }

    public HashMap<Integer, AbilityCategory> getAllFAMainCategories() throws CitizenReportException {
        try {
            return functionalAbilityDAO.getAllMainAbilityCategories();
        } catch (SQLException e) {
            throw new CitizenReportException("Error while retrieving Citizen's data report from the database",e);
        }
    }

    public HashMap<Integer, List<Pair<HealthCategory, Condition>>> getConditionsFromCitizen(Citizen citizen) throws CitizenReportException {
        try {
            return healthConditionDAO.getConditionsFromCitizen(citizen.getId());
        } catch (SQLException e) {
            throw new CitizenReportException("Error while retrieving Citizen's data report from the database",e);
        }
    }

    public HashMap<Integer, HealthCategory> getAllConditionsMainCategories() throws CitizenReportException {
        try {
            return healthConditionDAO.getAllMainHealthCategories();
        } catch (SQLException e) {
            throw new CitizenReportException("Error while retrieving Citizen's data report from the database",e);
        }
    }
}
