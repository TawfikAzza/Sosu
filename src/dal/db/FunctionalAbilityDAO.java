package dal.db;

import be.*;
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
    private final ConnectionManager cm;

    public FunctionalAbilityDAO() throws IOException {
        cm = new ConnectionManager();
    }
    /**
     * Author : Tawfik
     *
     * Method in charge of returning a list of Main abilityCategories object
     * with the subcategories associated to it (the subcategories are stored in
     * the subCategories List with is held in the AbilityCategory object.)
     * This method is called by the Functional Ability management Controller in charge of the input of values
     * for the citizen.
     * *****/
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
    /***
     * Author: Tawfik
     *
     * Method in charge of retrieving the Main functional abilty category and returning it as
     * an HashMap<Integer,AbilityCategory>.
     * This method is called by either the Report on functional abilities or
     * the Controller in charge of managing the input of values in the functional abilities
     * of the citizen.
     *
     * **/
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

    /***
     * Author: Tawfik
     *
     * Method in charge of retrieving the Functional Ability subcategories and returning it as
     * an ArrayList of AbilityCategory object.
     * This method is called by either the Report on functional abilities controller via the getAbilitiesFromCitizen
     * method, or by
     * the Controller in charge of managing the input of values in the functional abilities
     * of the citizen.
     *
     * **/
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
    /**
     * Author: Tawfik
     *
     * Method that retrieve a specific functional ability associated to a subcategory form the
     * Abilities table.
     * ****/
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
                abilitySearched.setGoals(rs.getString("citizenGoals"));
            }
        }
        return abilitySearched;
    }
    /***
     * Author : Tawfik
     * Add a functional ability in the database.
     * **/
    public void addAbility(Ability ability) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlInsert = "INSERT INTO Abilities VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setInt(1,ability.getCategoryID());
            pstmt.setInt(2,ability.getCitizenID());
            pstmt.setInt(3,ability.getScore());
            pstmt.setInt(4,ability.getStatus());
            pstmt.setString(5, ability.getGoals());
            pstmt.execute();
        }
    }
    /***
     * Author: Tawfik
     * Update the functional abilities of a citizen
     * This method is pretty straightforward
     * Note however that in order to find the right ability, I do not use the ID of the abilities table
     * but rather the ID of the Citizen as well as the ID of the subcategory it is associated to.
     * This is made possible because only one ability state exists for one subcategory.
     */

    public void updateAbility(Ability ability) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sqlUpdate = "UPDATE Abilities set score = ?, status= ?,citizenGoals=? " +
                    " WHERE categoryID=? AND citizenID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlUpdate);
            pstmt.setInt(1,ability.getScore());
            pstmt.setInt(2,ability.getStatus());
            pstmt.setString(3,ability.getGoals());
            pstmt.setInt(4,ability.getCategoryID());
            pstmt.setInt(5,ability.getCitizenID());
            pstmt.execute();
        }
    }
    /***
     * Author : Tawfik
     * This method is in charge of retrieving all the sensible information pertaining to the
     * Functional Abilities of a Citizen and rearranging them as cleanly as possible for later use
     * in the displaying of the Functional Abilities side of the report to the student.
     * This method take the Citizen Id as parameter.
     * @param : citizenID
     * @return : An Hashmap of an <Integer of the main class,Pair List containing the subcategory and the condition
     * associate to it.>
     *
     * Note: do not change this method without first understanding it inner working as the way it has been implemented
     * and executed is kind of complicated. (well for an old bat like me at least...)
     *
     * ****/
    public HashMap<Integer,List<Pair<AbilityCategory, Ability>>> getAbilitiesFromCitizen(int idCitizen) throws SQLException {
        //HashMap which will contain the subcategories filled on this particular citizen
        HashMap<Integer, AbilityCategory> categoryHashMap = new HashMap<>();
        //HashMAp which contain the Condition report found for this particular Citizen in the database.
        HashMap<Integer,Ability> abilityHashMap = new HashMap<>();
        //HashMap which will store the temporary result of the task, I need to first have the Abilities and subcategories
        //associated to be tightly linked together, this the way I do it.
        HashMap<AbilityCategory,Ability> hashMapResult = new HashMap<>();
        //HasMap which will store the results previously compiled in the "hashMapResult" and associating t with the Main category
        //associated (beware not the subcategory, the previous hashMap is in charge of it.)
        //For that purpose I create a HashMap which have the id of the main category as the Key and
        //a List of all the subcategories/Functional Abilities associated to it.

        //SubCategory HashMap initialization :
        HashMap<Integer,List<Pair<AbilityCategory,Ability>>> hashMapResultFinal = new HashMap<>();
        for(AbilityCategory abilityCategory : getAllSubCategories()) {
            categoryHashMap.put(abilityCategory.getId(),abilityCategory);
        }

        try (Connection connection = cm.getConnection()) {
            //THe query is straighforward and do no need comments.
            //The only thing is that this query's result will be used to fill the HashMap of functional Abilities
            //with the right values.
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
                ability.setGoals(rs.getString("citizenGoals"));
                abilityHashMap.put(ability.getId(),ability);
            }
            //Filling the HashMap of the temporary results with the right SubCategory/Functional abilities values
            for (Map.Entry entry: abilityHashMap.entrySet()) {
                Ability ability = (Ability) entry.getValue();
                AbilityCategory abilityCategory = categoryHashMap.get(ability.getCategoryID());
                if(abilityCategory != null) {
                    hashMapResult.put(abilityCategory,ability);
                    // System.out.println(healthCategory.getName()+" "+condition.getDescription());
                }
            }
            //For the final result HashMap, as it contains a List as the second argument,
            //I have to first initialize it with an List on each its entry, or I'll get a NullPOinter Exception
            //when I try to use it for the first time.
            //Must have a better way to do it, I just did not find it.
            for (Map.Entry entry:hashMapResult.entrySet()) {
                AbilityCategory abilityCategory = (AbilityCategory) entry.getKey();
                Ability ability = (Ability) entry.getValue();
                hashMapResultFinal.put(abilityCategory.getSid(),new ArrayList<>());
            }
            //For each values of the subacategories/Abilities, found in the hashMapResult
            //I had the couple to the associated index of the Parent category of the subcategory currently parsed.
            //As it is an hashMap, the addition would only happen once per key, but I use this way of working
            //to add the subcategory/Ability Pair to the List of the currently parsed key/index of the HashMap.
            //The result is that at the end, I have a List of all the Subcategories/Abilities arranged
            //by the ID of the main category, ready to be displayed in an orderly manner by the Controller.
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
