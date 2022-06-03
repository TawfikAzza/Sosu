package bll;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.util.GlobalVariables;
import dal.db.CitizenDAO;
import dal.db.CitizenFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitizenManager {

    private final CitizenFacade citizenFacade;
    private final CitizenDAO citizenDAO;



    public CitizenManager() throws CitizenException {
        try {
            this.citizenFacade = new CitizenFacade();
            this.citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            throw new CitizenException("Error while connecting to the database",e);
        }
    }

    public Citizen createNewCitizen(Citizen newCitizen) throws CitizenException {
        //We use a facade cause when copying a citizen you also have to copy the case, general info, functional ability
        // and health conditions which each are their own separate entities and have separate CRUD operations by default.
        return citizenFacade.copyCitizenToDb(newCitizen, true);
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

    public List<Citizen> getCitizens() throws CitizenException {
        return citizenDAO.getCitizens(GlobalVariables.getCurrentSchool().getId(), false);
    }

    public List<Citizen> getTemplates() throws CitizenException {
        return citizenDAO.getCitizens(GlobalVariables.getCurrentSchool().getId(), true);
    }

    public Citizen copyTempToCit(Citizen template) throws CitizenException {
        return citizenFacade.copyCitizenToDb(template, false);
    }

    public Citizen copyCitToTemp(Citizen citizen) throws CitizenException {
        return citizenFacade.copyCitizenToDb(citizen, true);
    }

    public List<Citizen> duplicateCitizen(Citizen citizen, int amount, boolean isTemplate) throws CitizenException {
        return citizenFacade.duplicateCitizen(citizen, amount, isTemplate);
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenDAO.assignCitizensToStudents(template,students);
    }
}
