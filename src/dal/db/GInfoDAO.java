package dal.db;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GInfoDAO {

    private final ConnectionManager connectionManager;

    public GInfoDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }

    public List<InfoCategory> getGInfoCategories() throws SQLException {
        List<InfoCategory> infoCategories = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            String sqlQuery = "SELECT * FROM [GeneralInfo]\n" +
                                "ORDER BY position ASC  ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String example = resultSet.getString("example");
                String definition = resultSet.getString("definition");

                infoCategories.add(new InfoCategory(id,name,example,definition));
            }
        }
        return infoCategories;
    }

    public void insertGeneralInformation(Citizen citizen,InfoCategory infoCategory,String infoContent) throws SQLException {
        int citizenID = citizen.getId();
        int infoCategoryID = infoCategory.getId();
        try(Connection connection = connectionManager.getConnection()){
            String sql = "INSERT INTO CitizenInfo VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,citizenID);
            preparedStatement.setInt(2,infoCategoryID);
            preparedStatement.setString(3,infoContent);
            preparedStatement.executeUpdate();
        }
    }
}
