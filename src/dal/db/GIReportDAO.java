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

public class GIReportDAO {


    private final ConnectionManager connectionManager;

    public GIReportDAO() throws IOException {
        connectionManager = new ConnectionManager();
    }


    public GIReportDAO getGIReport(Citizen citizen, InfoCategory selectedInfoCategory) throws SQLException {
        GeneralInfo generalInfoSearched = null;
        int citizenID = citizen.getId();
        int infoCategoryID = selectedInfoCategory.getId();
        try (Connection con = connectionManager.getConnection()){
            String sql = "Select * from CitizenInfo where CitizenID = ? and CategoryID IN (Select ID from GeneralInfo)";
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, citizen.getId());

            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                generalInfoSearched = new GeneralInfo(resultSet.getInt("id"),
                resultSet.getInt("citizenID"),
                resultSet.getInt("categoryID"),
                resultSet.getString("infoContent"));
            }

            return null;
        }


    }

}






