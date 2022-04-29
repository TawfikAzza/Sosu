package dal.db;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
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

public class FunctionalAbilityDAO {
    private ConnectionManager cm;

    public FunctionalAbilityDAO() throws IOException {
        cm = new ConnectionManager();
    }
    public List<AbilityCategory> getAllCategoriesTree() throws SQLException {
        List<AbilityCategory> abilityCategories = new ArrayList<>();
        HashMap<Integer,AbilityCategory> mainAbilityCategories = getAllMainAbilityCategories();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "Select FunctionCategories.*, FunctionCategories.sid " +
                    "FROM FunctionCategories where id in (SELECT id from FunctionCategories where sid IS NOT NULL)";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);

            ResultSet rs = pstsmt.executeQuery();
            while (rs.next()) {
                AbilityCategory abilityCategory = new AbilityCategory(rs.getInt("id"),rs.getString("name"));
                mainAbilityCategories.get(rs.getInt("sid")).getSubCategories().add(abilityCategory);
            }

        }
        abilityCategories = mainAbilityCategories.values().stream().toList();
        return abilityCategories;
    }

    private HashMap<Integer, AbilityCategory> getAllMainAbilityCategories() throws SQLException {
        HashMap<Integer,AbilityCategory> allCategories = new HashMap<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM FunctionCategories WHERE SID IS NULL";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                AbilityCategory abilityCategory = new AbilityCategory(rs.getInt("id"),rs.getString("name"));
                abilityCategory.setSid(rs.getInt("sid"));
                allCategories.put(abilityCategory.getId(),abilityCategory);
            }
        }
        return allCategories;
    }

    public Ability getAbility(AbilityCategory abilityCategory, Citizen citizen) throws SQLException {
        Ability abilitySearched = null;
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Abilities WHERE categoryID=? AND citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
            pstmt.setInt(1,abilityCategory.getId());
            pstmt.setInt(2,citizen.getId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                abilitySearched = new Ability(rs.getInt("id"),
                                            rs.getInt("categoryID"),
                                            rs.getInt("citizenID"),
                                            rs.getInt("score"),
                                            rs.getInt("status") );
            }
        }
        return abilitySearched;
    }
}
