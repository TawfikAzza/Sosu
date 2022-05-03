package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import dal.db.CitizenDAO;
import dal.db.CitizenFacade;
import dal.db.GetTemplatesFacade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CitizenManager {

    CitizenFacade citizenFacade;
    CitizenDAO citizenDAO;


    public CitizenManager() throws CitizenException {
        try {
            this.citizenFacade = new CitizenFacade();
            this.citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            throw new CitizenException("Error while connecting to the database",e);
        }
    }

    public Citizen createNewCitizen(Citizen newCitizen) throws CitizenException {
        return citizenFacade.addCitizenToDB(newCitizen, true);
    }


    public Citizen editCitizen(Citizen citizenToEdit, Citizen newCitizen) throws CitizenException {
        try {
            return citizenDAO.editCitizen(citizenToEdit,newCitizen);
        } catch (SQLException e) {
            throw new CitizenException("Error while editing data",e);
        }
    }
}
