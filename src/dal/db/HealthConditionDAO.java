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

    /***
     * Creator : Tawfik
     * This method is in charge of retrieving all the sensible information pertaining to the
     * Health Condition of a Citizen and rearranging them as cleanly as possible for later use
     * in the displaying of the Health condition side of the report to the student.
     * This method take the Citizen Id as parameter.
     * @param : citizenID
     * @return : An Hashmap of an <Integer of the main class,Pair List containing the subcategory and the condition
     * associate to it.
     *
     * Note: do not change this method without first understanding it inner working as the way it has been implemented
     * and executed is kind of complicated. (well for an old bat like me at least...)
     *
     * ****/

    public HashMap<Integer,List<Pair<HealthCategory,Condition>>> getConditionsFromCitizen(int idCitizen) throws SQLException {
        //HashMap which will contain the subcategories filled on this particular citizen
        HashMap<Integer, HealthCategory> categoryHashMap = new HashMap<>();
        //HashMAp which contain the Condition report found for this particular Citizen in the database.
        HashMap<Integer,Condition> conditionHashMap = new HashMap<>();
        //HashMap which will store the temporary result of the task, I need to first have the Condition and subcategories
        //associated to be tightly linked together, this the way I do it.
        HashMap<HealthCategory,Condition> hashMapResult = new HashMap<>();
        //HasMap which will store the results previously compiled in the "hashMapResult" and associating t with the Main category
        //associated (beware not the subcategory, the previous hashMap is in charge of it.
        //For that purpose I create a HashMap which have the id of the main category as the Key and
        //a List of all the subcategories/COnditions associated to it.

        //SubCategory HashMap initialization :
        HashMap<Integer,List<Pair<HealthCategory,Condition>>> hashMapResultFinal = new HashMap<>();
        for(HealthCategory healthCategory : getAllSubCategories()) {
            categoryHashMap.put(healthCategory.getId(),healthCategory);
        }

        try (Connection connection = cm.getConnection()) {
            //THe query is straighforward and do no need comments.
            //The only thing is that this query's result will be used to fill the HashMap of Conditions
            //with the right values.
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
            //Filling the HashMap of the temporary results with the right SubCategory/Condition values
            for (Map.Entry entry: conditionHashMap.entrySet()) {
              Condition condition = (Condition) entry.getValue();
              HealthCategory healthCategory = categoryHashMap.get(condition.getCategoryID());
              if(healthCategory != null) {
                hashMapResult.put(healthCategory,condition);
                 // System.out.println(healthCategory.getName()+" "+condition.getDescription());
              }
            }

            //For the final result HashMap, as it contains a List as the second argument,
            //I have to first initialize it with an List on each its entry, or I'll get a NullPOinter Exception
            //when I try to use it for the first time.
            //Must have a better way to do it, I just did not find it.
            for (Map.Entry entry:hashMapResult.entrySet()) {
                HealthCategory healthCategory = (HealthCategory) entry.getKey();
                Condition condition = (Condition) entry.getValue();
                hashMapResultFinal.put(healthCategory.getSid(),new ArrayList<>());
            }
            //For each values of the subacategories/Conditions, found in the hashMapResult
            //I had the couple to the associated index of the Parent category of the subcategory currently parsed.
            //Has it is an hashMap, the addition would only happen once per key, but I use this way of working
            //to add the subcategory/Condition Pair to the List of the currently parsed key/index of the HashMap.
            //The result is that at the end, I have a List of all the Subcategories/Conditions arranged
            //by the ID of the main category, ready to be displayed in an orderly manner by the Controller.

            for (Map.Entry entry:hashMapResult.entrySet()) {
                HealthCategory healthCategory = (HealthCategory) entry.getKey();
                Condition condition = (Condition) entry.getValue();
                //System.out.println(healthCategory.getSid()+" "+healthCategory.getName()+" "+condition.getDescription());

                hashMapResultFinal.get(healthCategory.getSid()).add(new Pair<>(healthCategory,condition));
                hashMapResultFinal.put(healthCategory.getSid(),hashMapResultFinal.get(healthCategory.getSid()));
            }
        }
        return hashMapResultFinal;
    }
}
