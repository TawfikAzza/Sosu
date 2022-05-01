package dal.db;

import be.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public HashMap<Integer, AbilityCategory> getAllMainAbilityCategories() throws SQLException {
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
    public List<AbilityCategory> getAllSubCategories() throws SQLException {
        List<AbilityCategory> abilityCategories = new ArrayList<>();

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "Select FunctionCategories.*, FunctionCategories.sid as sidMain " +
                    "FROM FunctionCategories where id in (SELECT id from FunctionCategories where sid IS NOT NULL) ORDER BY sidMain";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);

            ResultSet rs = pstsmt.executeQuery();
            while (rs.next()) {
                AbilityCategory abilityCategory = new AbilityCategory(rs.getInt("id"),rs.getString("name"));
                abilityCategory.setSid(rs.getInt("sid"));
                abilityCategories.add(abilityCategory);
            }

        }

        return abilityCategories;
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

    public void addAbility(Ability ability) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlInsert = "INSERT INTO Abilities VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setInt(1,ability.getCategoryID());
            pstmt.setInt(2,ability.getCitizenID());
            pstmt.setInt(3,ability.getScore());
            pstmt.setInt(4,ability.getStatus());
            pstmt.execute();
        }
    }

    public void updateAbility(Ability ability) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlUpdate = "UPDATE Abilities set score = ?, status= ? " +
                    " WHERE categoryID=? AND citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlUpdate);
            pstmt.setInt(1,ability.getScore());
            pstmt.setInt(2,ability.getStatus());
            pstmt.setInt(3,ability.getCategoryID());
            pstmt.setInt(4,ability.getCitizenID());
            pstmt.execute();
        }
    }

    public HashMap<Integer,List<Pair<AbilityCategory, Ability>>> getAbilitiesFromCitizen(int idCitizen) throws SQLException {
        HashMap<Integer, AbilityCategory> categoryHashMap = new HashMap<>();
        HashMap<Integer,Ability> abilityHashMap = new HashMap<>();
        HashMap<AbilityCategory,Ability> hashMapResult = new HashMap<>();
        HashMap<Integer,List<Pair<AbilityCategory,Ability>>> hashMapResultFinal = new HashMap<>();
        for(AbilityCategory abilityCategory : getAllSubCategories()) {
            categoryHashMap.put(abilityCategory.getId(),abilityCategory);
        }

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Abilities WHERE citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
            pstmt.setInt(1,idCitizen);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Ability ability = new Ability(rs.getInt("id"),
                        rs.getInt("categoryID"),
                        rs.getInt("citizenID"),
                        rs.getInt("score"),
                        rs.getInt("status") );
                abilityHashMap.put(ability.getId(),ability);
            }

            for (Map.Entry entry: abilityHashMap.entrySet()) {
                Ability ability = (Ability) entry.getValue();
                AbilityCategory abilityCategory = categoryHashMap.get(ability.getCategoryID());
                if(abilityCategory != null) {
                    hashMapResult.put(abilityCategory,ability);
                    // System.out.println(healthCategory.getName()+" "+condition.getDescription());
                }
            }


            for (Map.Entry entry:hashMapResult.entrySet()) {
                AbilityCategory abilityCategory = (AbilityCategory) entry.getKey();
                Ability ability = (Ability) entry.getValue();
                hashMapResultFinal.put(abilityCategory.getSid(),new ArrayList<>());
            }
            for (Map.Entry entry:hashMapResult.entrySet()) {
                AbilityCategory abilityCategory = (AbilityCategory) entry.getKey();
                Ability ability = (Ability) entry.getValue();
               // System.out.println(abilityCategory.getSid()+" "+abilityCategory.getName()+" "+ability.getScore());

                hashMapResultFinal.get(abilityCategory.getSid()).add(new Pair<>(abilityCategory,ability));
                hashMapResultFinal.put(abilityCategory.getSid(),hashMapResultFinal.get(abilityCategory.getSid()));
            }
        }
        return hashMapResultFinal;
    }
}
