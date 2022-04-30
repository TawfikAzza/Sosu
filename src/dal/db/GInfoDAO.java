package dal.db;

import be.GeneralInfo;
import be.InfoCategory;
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
}
