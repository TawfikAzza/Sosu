package gui.Model;

import be.AbilityCategory;
import be.Citizen;
import be.Condition;
import be.HealthCategory;
import bll.CategoryManager;
import bll.exceptions.HealthCategoryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryModel {

    private CategoryManager manager;


    public CategoryModel() throws HealthCategoryException {
        manager = new CategoryManager();
    }

    public List<HealthCategory> getHealthCategories() throws HealthCategoryException {
        return manager.getHealthCategories();
    }

    public Condition getCondition(HealthCategory healthCategory, Citizen citizen) throws HealthCategoryException {
        return manager.getCondition(healthCategory,citizen);
    }

    public void addCondition(Condition condition) throws HealthCategoryException {
        manager.addCondition(condition);
    }

    public void updateCondition(Condition condition) throws HealthCategoryException {
        manager.updateCondition(condition);
    }
}
