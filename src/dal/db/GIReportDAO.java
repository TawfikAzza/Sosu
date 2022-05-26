package dal.db;

import be.*;
import dal.ConnectionManager;
import dal.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GIReportDAO {


    //private final ConnectionManager connectionManager;
    private DBCPDataSource dataSource;


    public GIReportDAO() throws IOException {
        //connectionManager = new ConnectionManager();
        dataSource = DBCPDataSource.getInstance();
    }


    public HashMap<String, String> getGIReport(Citizen citizen) throws SQLException {
        String nameGI = null;
        String infoContent = null;
        HashMap<String, String> hashMap = new HashMap<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "Select GeneralInfo.name as name, CitizenInfo.infoContent as infoContent from GeneralInfo,CitizenInfo where GeneralInfo.id=CitizenInfo.categoryID and CitizenID = ? and CategoryID IN (Select ID from GeneralInfo)";
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, citizen.getId());

            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {

                nameGI = resultSet.getString("name");
                infoContent = resultSet.getString("infoContent");
                hashMap.put(nameGI, infoContent);
            }
            System.out.println("IN GReportDAO SIZE: " + hashMap.size());
            return hashMap;
        }


    }


}






