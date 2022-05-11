package bll;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import bll.exceptions.GeneralInfoException;
import dal.db.GIReportDAO;
import dal.db.GInfoDAO;

import java.io.IOException;
import java.sql.SQLException;

public class GIReportManger {

    GIReportDAO giReportDAO;


    public GIReportManger() throws GeneralInfoException {
        try {
            giReportDAO = new GIReportDAO();
        } catch (Exception e) {
            throw new GeneralInfoException("Error while connecting to the database", e);
        }
    }


    public void getGeneralInfoReport(Citizen citizen, InfoCategory infoCategory, GeneralInfo generalInfo) throws SQLException {


    }
}
