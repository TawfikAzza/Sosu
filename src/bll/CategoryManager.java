package bll;

import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import dal.db.HealthConditionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryManager {

    HealthConditionDAO healthConditionDAO;

    public CategoryManager() throws HealthCategoryException {
        try {
            healthConditionDAO = new HealthConditionDAO();
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
}
