package bll;

import be.Citizen;
import bll.exceptions.GIReportException;
import dal.db.GIReportDAO;

import java.sql.SQLException;
import java.util.HashMap;

public class GIReportManger {

    private GIReportDAO giReportDAO;




    public GIReportManger() throws GIReportException {
        try {
            giReportDAO = new GIReportDAO();
        } catch (Exception e) {
            throw new GIReportException("Error while connecting to the database", e);
        }
    }


    public HashMap<String, String> getGIReport(Citizen citizen) {
        HashMap<String,String> hashMap=new HashMap<>();
        try {
            hashMap=  giReportDAO.getGIReport(citizen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;


    }


}
