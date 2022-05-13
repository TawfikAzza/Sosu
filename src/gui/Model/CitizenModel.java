package gui.Model;

import be.Citizen;
import be.Student;
import bll.CitizenManager;
import bll.exceptions.CitizenException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CitizenModel {

    private final CitizenManager citizenManager;
    private ObservableList<Citizen> obsCitizens;
    private  ObservableList<Citizen> templates;
    private static CitizenModel instance;

    private CitizenModel() throws CitizenException {
        this.citizenManager = new CitizenManager();
        initObsLists();
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

    private List<Citizen> getCitizens() throws CitizenException {
        return citizenManager.getCitizens();
    }

    private List<Citizen> getTemplates() throws CitizenException {
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
        obsCitizens.add(citizenManager.copyTempToCit(template));
    }

    public void copyCitToTemp(Citizen citizen) throws CitizenException {
        templates.add(citizenManager.copyCitToTemp(citizen));
    }

    public void duplicateTemplates(Citizen citizen, int amount) throws CitizenException {
        templates.addAll(citizenManager.duplicateTemplates(citizen, amount));
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenManager.assignCitizensToStudents(template,students);
    }

    private void initObsLists() throws CitizenException {
        ObservableList<Citizen> citizens = FXCollections.observableArrayList();
        ObservableList<Citizen> templates = FXCollections.observableArrayList();
        citizens.addAll(getCitizens());
        templates.addAll(getTemplates());
        this.obsCitizens = citizens;
        this.templates = templates;
    }

    public static CitizenModel getInstance() throws CitizenException {
        if(instance==null)
        {
            instance = new CitizenModel();
        }
        return instance;
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
}
