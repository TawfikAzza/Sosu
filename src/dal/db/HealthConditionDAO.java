package dal.db;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
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
    public List<HealthCategory> getAllSubCategories() throws SQLException {
        List<HealthCategory> healthCategories = new ArrayList<>();
        HashMap<Integer,HealthCategory> mainHealthCategories = getAllMainHealthCategories();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "Select HealthCategories.*, HealthCategories.sid as sidMain " +
                    "FROM HealthCategories where id in (SELECT id from HealthCategories where sid IS NOT NULL) ORDER BY sidMain";
            PreparedStatement pstsmt = connection.prepareStatement(sqlSelect);

            ResultSet rs = pstsmt.executeQuery();
            while (rs.next()) {
                HealthCategory healthCategory = new HealthCategory(rs.getInt("id"),rs.getString("name"));
                healthCategory.setSid(rs.getInt("sid"));
                healthCategories.add(healthCategory);
            }

        }

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

    public void addCondition(Condition condition) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlInsert = "INSERT INTO CONDITIONS VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setInt(1, condition.getCategoryID());
            pstmt.setInt(2, condition.getCitizenID());
            pstmt.setString(3, condition.getDescription());
            pstmt.setInt(4, condition.getStatus());
            pstmt.setString(5, condition.getFreeText());
            pstmt.setString(6, condition.getGoal());

            pstmt.execute();

        }
    }

    public void updateCondition(Condition condition) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlUpdate = "UPDATE CONDITIONS set description=?, status = ?, text= ?, goal= ?" +
                    " WHERE categoryID=? AND citizenID=?  ";
            PreparedStatement pstmt = connection.prepareStatement(sqlUpdate);

            pstmt.setString(1, condition.getDescription());
            pstmt.setInt(2, condition.getStatus());
            pstmt.setString(3, condition.getFreeText());
            pstmt.setString(4, condition.getGoal());
            pstmt.setInt(5,condition.getCategoryID());
            pstmt.setInt(6,condition.getCitizenID());

            pstmt.execute();

        }
    }
    public HashMap<Integer,List<Pair<HealthCategory,Condition>>> getConditionsFromCitizen(int idCitizen) throws SQLException {
        HashMap<Integer, HealthCategory> categoryHashMap = new HashMap<>();
        HashMap<Integer,Condition> conditionHashMap = new HashMap<>();
        HashMap<HealthCategory,Condition> hashMapResult = new HashMap<>();
        HashMap<Integer,List<Pair<HealthCategory,Condition>>> hashMapResultFinal = new HashMap<>();
        for(HealthCategory healthCategory : getAllSubCategories()) {
            categoryHashMap.put(healthCategory.getId(),healthCategory);
        }

        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Conditions WHERE citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
            pstmt.setInt(1,idCitizen);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Condition condition = new Condition(rs.getInt("id")
                        ,rs.getInt("categoryID")
                        , rs.getInt("citizenID")
                        , rs.getString("description")
                        , rs.getInt("status")
                        , rs.getString("text")
                        ,rs.getString("goal") );
                conditionHashMap.put(condition.getId(),condition);
            }

          for (Map.Entry entry: conditionHashMap.entrySet()) {
              Condition condition = (Condition) entry.getValue();
              HealthCategory healthCategory = categoryHashMap.get(condition.getCategoryID());
              if(healthCategory != null) {
              hashMapResult.put(healthCategory,condition);
                 // System.out.println(healthCategory.getName()+" "+condition.getDescription());
              }
          }

          List<Pair<HealthCategory,Condition>> listTmp = new ArrayList<>();
          int currentSid = 0;
          boolean flagFirst = false;
          for (Map.Entry entry: hashMapResult.entrySet()) {
              HealthCategory healthCategory = (HealthCategory) entry.getKey();
              Condition condition = (Condition) entry.getValue();

              if(!flagFirst) {
                  currentSid=healthCategory.getSid();
                  flagFirst = true;
              }
              if(currentSid != healthCategory.getSid()) {
                  List<Pair<HealthCategory,Condition>> listTmp2 = new ArrayList<>();
                  System.out.println("LIST SIZE:"+listTmp.size());
                  hashMapResultFinal.put(healthCategory.getSid(),listTmp);
                  listTmp = listTmp2;
              }
              Pair<HealthCategory,Condition> pair = new Pair<>(healthCategory,condition);
              listTmp.add(pair);
              System.out.println("added : "+healthCategory.getName()+" "+condition.getDescription());
              currentSid = healthCategory.getSid();
              }
        }
        return hashMapResultFinal;
    }
}
