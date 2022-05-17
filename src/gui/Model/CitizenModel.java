package gui.Model;

import be.Citizen;
import be.Student;
import bll.CitizenManager;
import bll.exceptions.CitizenException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class CitizenModel {

    private final CitizenManager citizenManager;
    private ObservableList<Citizen> obsCitizens;
    private FilteredList<Citizen> templates;
    private static CitizenModel instance;

    private CitizenModel() throws CitizenException {
        this.citizenManager = new CitizenManager();
        initObsLists();
    }


    public Citizen createNewCitizen(Citizen newCitizen) throws CitizenException {
        Citizen citizen = citizenManager.createNewCitizen(newCitizen);
        //You cant add directly to the filtered list, that would defeat the purpose of it being filtered
        //rather you have to add to the underlying unfiltered list, which you can't really do because the compiler
        //infer's the type of the list to be of wildcard capture so you have to cast it
        ObservableList<Citizen> underlyingList = ((ObservableList<Citizen>) templates.getSource());
        underlyingList.add(citizen);
        return citizen;
    }

    public Citizen editCitizen(Citizen citizenToEdit) throws CitizenException {
        return citizenManager.editCitizen(citizenToEdit);
    }

    public void deleteCitizen(Citizen selectedCitizen) throws CitizenException {
        citizenManager.deleteCitizen(selectedCitizen);
        obsCitizens.remove(selectedCitizen);
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

    public FilteredList<Citizen> getTemplatesObs()
    {
        return templates;
    }

    public void copyTempToCit(Citizen template) throws CitizenException {
        obsCitizens.add(citizenManager.copyTempToCit(template));
    }

    public void copyCitToTemp(Citizen citizen) throws CitizenException {
        templates.add(citizenManager.copyCitToTemp(citizen));
    }

    public void duplicateCitizen(Citizen citizen, int amount, boolean isTemplate) throws CitizenException {
        if(isTemplate) {
            templates.addAll(citizenManager.duplicateCitizen(citizen, amount, true));
        }
        if(!isTemplate)
        {
            obsCitizens.addAll(citizenManager.duplicateCitizen(citizen, amount, false));
        }
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

        //Filtered list extends observable list so you wrap the observable list inside it and if you want
        //the items to be showed by default you set the predicate to true by default for all citizens
        this.templates = new FilteredList<>(templates, citizen -> true);
    }

    public static CitizenModel getInstance() throws CitizenException {
        if(instance==null)
        {
            instance = new CitizenModel();
        }
        return instance;
    }
}
