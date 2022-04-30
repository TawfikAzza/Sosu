import be.AbilityCategory;
import be.HealthCategory;
import dal.db.FunctionalAbilityDAO;
import dal.db.HealthConditionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DAOTest {
    public static void main(String[] args) throws SQLException, IOException {
       // getAllCategories();
      //  getAllCategoriesTree();
        getAllAbilityCategoriesTree();
    }
    private static void getAllCategories() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        healthConditionDAO.getAllMainHealthCategories();
    }

    private static void getAllCategoriesTree() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        List<HealthCategory> healthCategoryList = healthConditionDAO.getAllCategoriesTree();
        for (HealthCategory healthCategory: healthCategoryList) {
            System.out.println("id "+ healthCategory.getId()+ " name "+healthCategory.getName()+ " size: "+healthCategory.getSubCategories().size());
            for (HealthCategory subCategory: healthCategory.getSubCategories()) {
                System.out.println("id "+ subCategory.getId()+ " name "+subCategory.getName()+ " size: "+subCategory.getSubCategories().size());
            }
        }
    }

    private static void getAllAbilityCategoriesTree() throws IOException, SQLException {
        FunctionalAbilityDAO functionalAbilityDAO = new FunctionalAbilityDAO();
        List<AbilityCategory> abilityCategoryList = functionalAbilityDAO.getAllCategoriesTree();
        for (AbilityCategory abilityCategory: abilityCategoryList) {
            System.out.println("id "+ abilityCategory.getId()+ " name "+abilityCategory.getName()+ " size: "+abilityCategory.getSubCategories().size());
            for (AbilityCategory subCategory: abilityCategory.getSubCategories()) {
                System.out.println("id "+ subCategory.getId()+ " name "+subCategory.getName()+ " size: "+subCategory.getSubCategories().size());
            }
        }
    }
}
