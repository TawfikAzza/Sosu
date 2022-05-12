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
    private ObservableList<Citizen> templates;

    public CitizenModel() throws CitizenException {
        this.citizenManager = new CitizenManager();
        this.obsCitizens = getCitizens();
        setTemplates();
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

    public ObservableList<Citizen> getTemplatesObs()
    {
        return templates;
    }

    private void setTemplates() throws CitizenException {
        this.templates = citizenManager.getTemplates();
    }

    public void copyTempToCit(Citizen template) throws CitizenException {
        Citizen added = citizenManager.copyTempToCit(template);
        if(added!=null)
        {
            obsCitizens.add(added);
        }
    }

    public void copyCitToTemp(Citizen citizen) throws CitizenException {
        Citizen added = citizenManager.copyCitToTemp(citizen);
        if(added!=null)
        {
            templates.add(added);
        }
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenManager.assignCitizensToStudents(template,students);
    }

    public void refreshTemplates() throws CitizenException {
        templates.clear();
        setTemplates();
    }
}
