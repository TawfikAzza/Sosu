package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import bll.util.GlobalVariables;
import dal.db.CitizenDAO;
import dal.db.CitizenFacade;
import dal.db.GetTemplatesFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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


    public Citizen editCitizen(Citizen citizenToEdit) throws CitizenException {

        try {
             return citizenDAO.editCitizen(citizenToEdit);
        } catch (SQLException e) {
            throw new CitizenException("Error while editing data",e);
        }
    }

    public void deleteCitizen(Citizen selectedCitizen) throws CitizenException {
        try {
            citizenDAO.deleteCitizen(selectedCitizen);
        } catch (SQLException e) {
            throw new CitizenException("Error while deleting data",e);
        }
    }

    public ObservableList<Citizen> getCitizens() throws CitizenException {
        List<Citizen> citizens = citizenDAO.getAllCitizens(GlobalVariables.getCurrentSchool().getId());
        ObservableList<Citizen> obsCits = FXCollections.observableArrayList();
        obsCits.addAll(citizens);
        return obsCits;
    }
}
