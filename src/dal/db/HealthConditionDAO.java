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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthConditionDAO {
    private ConnectionManager cm;

    public HealthConditionDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<HealthCategory> getAllCategoriesTree() throws SQLException {
        List<HealthCategory> healthCategories = new ArrayList<>();
        HashMap<Integer,HealthCategory> mainHealthCategories = getAllMainHealthCategories();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "Select HealthCategories.*, HealthCategories.sid " +
                    "FROM HealthCategories where id in (SELECT id from HealthCategories where sid IS NOT NULL)";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);

            ResultSet rs = pstsmt.executeQuery();
            while (rs.next()) {
                HealthCategory healthCategory = new HealthCategory(rs.getInt("id"),rs.getString("name"));
                mainHealthCategories.get(rs.getInt("sid")).getSubCategories().add(healthCategory);
            }

        }
        healthCategories = mainHealthCategories.values().stream().toList();
        return healthCategories;
    }

//TODO: FINISH THE Object HealthCategory Creation and add the rest of the attributes in it !!!
    public HashMap<Integer,HealthCategory> getAllMainHealthCategories() throws SQLException {
        HashMap<Integer,HealthCategory> allCategories = new HashMap<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM HealthCategories WHERE SID IS NULL";
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

    public Condition getCondition(HealthCategory healthCategory, Citizen citizen) throws SQLException {
        Condition conditionSearched = null;
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM CONDITIONS WHERE categoryID = ? AND citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,healthCategory.getId());
            pstmt.setInt(2,citizen.getId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                conditionSearched = new Condition(rs.getInt("id")
                                                 ,rs.getInt("categoryID")
                                                , rs.getInt("citizenID")
                                                , rs.getString("description")
                                                , rs.getInt("status")
                                                , rs.getString("text")
                                                ,rs.getString("goal") );
            }
        }
        return conditionSearched;
    }
}
