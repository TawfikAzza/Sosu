package gui.Model;

import be.*;
import bll.CategoryManager;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;


import java.util.List;

public class CategoryModel {

    private final CategoryManager manager;


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

    public List<AbilityCategory> getAbilityCategories() throws AbilityCategoryException {
        return manager.getAbilityCategories();
    }

    public Ability getAbility(AbilityCategory abilityCategory, Citizen citizen) throws AbilityCategoryException {
        return manager.getAbility(abilityCategory,citizen);
    }

    public void addAbility(Ability ability) throws AbilityCategoryException {
        manager.addAbility(ability);
    }

    public void updateAbility(Ability ability) throws AbilityCategoryException {
        manager.updateAbility(ability);
    }
}
