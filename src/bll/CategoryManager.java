package bll;

import be.*;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import dal.db.FunctionalAbilityDAO;
import dal.db.HealthConditionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryManager {

    private HealthConditionDAO healthConditionDAO;
    private FunctionalAbilityDAO functionalAbilityDAO;
    public CategoryManager() throws HealthCategoryException {
        try {
            healthConditionDAO = new HealthConditionDAO();
            functionalAbilityDAO = new FunctionalAbilityDAO();
        } catch (IOException e) {
            throw new HealthCategoryException("Error while connecting to the database",e);
        }
    }

    public List<HealthCategory> getHealthCategories() throws HealthCategoryException {
        try {
            return healthConditionDAO.getAllCategoriesTree();
        } catch (SQLException e) {
            throw new HealthCategoryException("Error while retrieving categories from the database",e);
        }
    }

    public Condition getCondition(HealthCategory healthCategory, Citizen citizen) throws HealthCategoryException {
        try {
            return healthConditionDAO.getCondition(healthCategory,citizen);
        } catch (SQLException e) {
            throw new HealthCategoryException("Error while retrieving condition from the database",e);
        }
    }

    public void addCondition(Condition condition) throws HealthCategoryException {
        try {
            healthConditionDAO.addCondition(condition);
        } catch (SQLException e) {
            throw new HealthCategoryException("Error while inserting a condition in the database",e);
        }
    }

    public void updateCondition(Condition condition) throws HealthCategoryException {
        try {
            healthConditionDAO.updateCondition(condition);
        } catch (SQLException e) {
            throw new HealthCategoryException("Error while updating a condition in the database",e);
        }
    }

    public List<AbilityCategory> getAbilityCategories() throws AbilityCategoryException {
        try {
            return functionalAbilityDAO.getAllCategoriesTree();
        } catch (SQLException e) {
           throw new AbilityCategoryException("Error while retrieving the Functional Abilities from the database",e);
        }

    }

    public Ability getAbility(AbilityCategory abilityCategory, Citizen citizen) throws AbilityCategoryException {
        try {
            return functionalAbilityDAO.getAbility(abilityCategory,citizen);
        } catch (SQLException e) {
            throw new AbilityCategoryException("Error while retrieving an ability report from the database",e);
        }
    }

    public void addAbility(Ability ability) throws AbilityCategoryException {
        try {
            functionalAbilityDAO.addAbility(ability);
        } catch (SQLException e) {
            throw new AbilityCategoryException("Error during the creation of an ability report in the database",e);
        }
    }

    public void updateAbility(Ability ability) throws AbilityCategoryException {
        try {
            functionalAbilityDAO.updateAbility(ability);
        } catch (SQLException e) {
            throw new AbilityCategoryException("Error while updating an ability report in the database",e);
        }
    }
}
