package dal.db;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GIReportDAO {


    private final ConnectionManager connectionManager;

    public GIReportDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }



    public GIReportDAO getGeneralInfoReport(Citizen citizen, InfoCategory infoCategory, GeneralInfo generalInfo) throws SQLException {
        GeneralInfo generalInfoSearched = null;
        int citizenID = citizen.getId();
        int infoCategoryID = infoCategory.getId();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROm CitizenInfo " +
                    " WHERE citizenID = ? AND categoryID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, citizenID);
            preparedStatement.setInt(2, infoCategoryID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                generalInfoSearched = new GeneralInfo(resultSet.getInt("id"),
                        resultSet.getInt("citizenID"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("infoContent"));
            }


        }
        return null;
    }
}





