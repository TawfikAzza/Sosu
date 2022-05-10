package gui.Model;

import be.Citizen;
import bll.CitizenManager;
import bll.exceptions.CitizenException;

public class CitizenModel {

    private CitizenManager citizenManager;

    public CitizenModel() throws CitizenException {
        citizenManager = new CitizenManager();
    }


    public Citizen createNewCitizen(Citizen newCitizen) throws CitizenException {
        return citizenManager.createNewCitizen(newCitizen);
    }

    public Citizen editCitizen(Citizen citizenToEdit) throws CitizenException {
         return citizenManager.editCitizen(citizenToEdit);
    }

    public void deleteCitizen(Citizen selectedCitizen) throws CitizenException {
        citizenManager.deleteCitizen(selectedCitizen);
    }
}
