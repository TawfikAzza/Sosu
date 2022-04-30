package bll;

import be.GeneralInfo;
import be.InfoCategory;
import bll.exceptions.CitizenException;
import dal.db.GInfoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GInfoManager {

    GInfoDAO infoDAO;

    public GInfoManager() throws CitizenException {
        try {
            infoDAO = new GInfoDAO();
        } catch (IOException e) {
            throw new CitizenException("Error while connecting to the database",e);
        }
    }

    public List<InfoCategory> getGInfoCategories() throws CitizenException {
        try {
            return infoDAO.getGInfoCategories();
        } catch (SQLException e) {
            throw new CitizenException("Error while retrieving data from the database",e);
        }
    }
}
