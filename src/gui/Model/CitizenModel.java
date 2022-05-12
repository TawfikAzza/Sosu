package gui.Model;

import be.Citizen;
import be.Student;
import bll.CitizenManager;
import bll.exceptions.CitizenException;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CitizenModel {

    private final CitizenManager citizenManager;
    private final ObservableList<Citizen> obsCitizens;
    private final ObservableList<Citizen> templates;
    private static CitizenModel instance;

    private CitizenModel() throws CitizenException {
        this.citizenManager = new CitizenManager();
        this.obsCitizens = getCitizens();
        this.templates = getTemplates();
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

    private ObservableList<Citizen> getTemplates() throws CitizenException {
        return citizenManager.getTemplates();
    }

    public ObservableList<Citizen> getObsListCitizens()
    {
        return obsCitizens;
    }

    public ObservableList<Citizen> getTemplatesObs()
    {
        return templates;
    }

    public void copyTempToCit(Citizen template) throws CitizenException {
        citizenManager.copyTempToCit(template);
        refreshTables();
    }

    public void copyCitToTemp(Citizen citizen) throws CitizenException {
        citizenManager.copyCitToTemp(citizen);
        refreshTables();
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenManager.assignCitizensToStudents(template,students);
    }

    public void refreshTemplates() throws CitizenException {
        this.templates.clear();
        this.templates.addAll(citizenManager.getTemplates());
    }

    public void refreshCitizens() throws CitizenException {
        this.obsCitizens.clear();
        this.obsCitizens.addAll(citizenManager.getCitizens());
    }

    public void refreshTables() throws CitizenException {
        refreshTemplates();
        refreshCitizens();
    }

    public static CitizenModel getInstance() throws CitizenException {
        if(instance==null)
        {
            instance = new CitizenModel();
        }
        return instance;
    }
}
