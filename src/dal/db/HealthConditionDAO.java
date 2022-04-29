package dal.db;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class HealthConditionDAO {
    private ConnectionManager cm;

    public HealthConditionDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<HealthCategory> getAllHealthConditionPerCategory() throws SQLException {
        HashMap<Integer,HealthCategory> healthCategoryHashMap = getAllHealthCategories();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "Select HealthCategories.name, HealthCategories.sid " +
                    "FROM HealthCategories where id in (SELECT id from HealthCategories where sid IS NOT NULL)";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);

            ResultSet rs = pstsmt.executeQuery();
            if (rs.next()) {

            }
        }
    return null;
    }

//TODO: FINISH THE Object HealthCategory Creation and add the rest of the attributes in it !!!
    public HashMap<Integer,HealthCategory> getAllHealthCategories() throws SQLException {
        HashMap<Integer,HealthCategory> allCategories = new HashMap<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM HealthCategories";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                HealthCategory healthCategory = new HealthCategory(rs.getInt("id"),rs.getString("name"));
                healthCategory.setSid(rs.getInt("sid"));
                allCategories.put(healthCategory.getId(),healthCategory);
            }
        }
        return allCategories;
    }
}
