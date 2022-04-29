import be.HealthCategory;
import dal.db.HealthConditionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DAOTest {
    public static void main(String[] args) throws SQLException, IOException {
       // getAllCategories();
        getAllCategoriesTree();
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
}
