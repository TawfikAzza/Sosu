package bll;

import be.Citizen;
import be.InfoCategory;
import bll.exceptions.GeneralInfoException;
import dal.db.CitizenDAO;
import dal.db.GIReportDAO;

import java.sql.SQLException;

public class GIReportManger {

    GIReportDAO giReportDAO;
    CitizenDAO citizenDAO;


    public GIReportManger() throws GeneralInfoException {
        try {
            giReportDAO = new GIReportDAO();
            this.citizenDAO = new CitizenDAO();
        } catch (Exception e) {
            throw new GeneralInfoException("Error while connecting to the database", e);
        }
    }


    public void getGIReport(Citizen citizen, InfoCategory selectedInfoCategory) {
        try {
            giReportDAO.getGIReport(citizen, selectedInfoCategory);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
