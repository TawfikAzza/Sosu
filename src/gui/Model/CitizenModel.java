package gui.Model;

import be.Citizen;
import bll.CitizenManager;
import bll.exceptions.CitizenException;

public class CitizenModel {

    private CitizenManager citizenManager;

    public CitizenModel() throws CitizenException {
        citizenManager = new CitizenManager();
    }


    public void createNewCitizen(Citizen newCitizen) throws CitizenException {
        citizenManager.createNewCitizen(newCitizen);
    }
}
