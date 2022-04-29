package gui.Model;

import be.AbilityCategory;
import be.HealthCategory;
import javafx.collections.ObservableList;

public class CategoryModel {

    private ObservableList<AbilityCategory> abilityCategories;
    private ObservableList<HealthCategory> healthCategories;

    public CategoryModel(){
        loadData();
    }

    private void loadData() {

    }

    public ObservableList getAbilityCategories() {
        return abilityCategories;
    }
}
