package gui.Model;

import be.*;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import bll.ReportManager;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class ReportModel {
    private ReportManager reportManager;
    public ReportModel() throws HealthCategoryException {
        reportManager = new ReportManager();
    }

    public HashMap<Integer, List<Pair<AbilityCategory, Ability>>> getAbilitiesFromCitizen(Citizen citizen) throws CitizenReportException {
        return reportManager.getAbilitiesFromCitizen(citizen);
    }

    public HashMap<Integer, AbilityCategory> getAllFAMainCategories() throws CitizenReportException {
        return reportManager.getAllFAMainCategories();
    }

    public HashMap<Integer, List<Pair<HealthCategory, Condition>>> getConditionsFromCitizen(Citizen citizen) throws CitizenReportException {
        return reportManager.getConditionsFromCitizen(citizen);
    }

    public HashMap<Integer, HealthCategory> getAllConditionsMainCategories() throws CitizenReportException {
        return reportManager.getAllConditionsMainCategories();
    }
}
