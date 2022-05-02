package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import dal.db.CitizenFacade;
import dal.db.GetTemplatesFacade;

import java.io.IOException;
import java.util.List;

public class CitizenManager {

    CitizenFacade citizenFacade;


    public CitizenManager() throws CitizenException {
        try {
            this.citizenFacade = new CitizenFacade();
        } catch (IOException e) {
            throw new CitizenException("Error while connecting to the database",e);
        }
    }

    public Citizen createNewCitizen(Citizen newCitizen) throws CitizenException {
        return citizenFacade.addCitizenToDB(newCitizen);
    }


}
