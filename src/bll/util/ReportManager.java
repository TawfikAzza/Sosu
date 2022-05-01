package bll.util;

import be.Citizen;
import be.CitizenReport;
import be.Condition;
import bll.exceptions.HealthCategoryException;
import dal.db.CitizenDAO;
import dal.db.FunctionalAbilityDAO;
import dal.db.HealthConditionDAO;
import gui.utils.DisplayMessage;


import java.io.IOException;
import java.util.ArrayList;
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

}
