package gui.Model;

import be.Citizen;
import bll.CitizenManager;
import bll.exceptions.CitizenException;
import javafx.collections.ObservableList;

import java.util.List;

public class CitizenModel {

    private final CitizenManager citizenManager;
    private ObservableList<Citizen> obsCitizens;

    public CitizenModel() throws CitizenException {
        this.citizenManager = new CitizenManager();
        this.obsCitizens = getCitizens();
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

    private ObservableList<Citizen> getCitizens() throws CitizenException {
        return citizenManager.getCitizens();
    }

    public ObservableList<Citizen> getObsListCitizens()
    {
        return obsCitizens;
    }
}
