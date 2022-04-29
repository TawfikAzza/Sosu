import dal.db.HealthConditionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class DAOTest {
    public static void main(String[] args) throws SQLException, IOException {
        getAllCategories();
    }
    private static void getAllCategories() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        healthConditionDAO.getAllHealthCategories();
    }
}
