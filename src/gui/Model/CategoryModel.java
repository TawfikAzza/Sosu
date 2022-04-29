package gui.Model;

import be.AbilityCategory;
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
}
