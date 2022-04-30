package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import dal.db.CitizenFacade;

import java.io.IOException;
import java.sql.SQLException;

public class CitizenManager {

    CitizenFacade citizenFacade;

    public CitizenManager() throws CitizenException {
        try {
            citizenFacade = new CitizenFacade();
        } catch (IOException e) {
            throw new CitizenException("Error while connecting to the database",e);
        }
    }

    public void createNewCitizen(Citizen newCitizen) throws CitizenException {
        try {
            citizenFacade.addCitizenToDB(newCitizen);
        } catch (SQLException e) {
            throw new CitizenException("Error whhile creating new citizen",e);
        }
    }
}
