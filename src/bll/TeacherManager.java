package bll;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import dal.db.CitizenFacade;
import dal.db.GetTemplatesFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherManager {

    GetTemplatesFacade templatesFacade;
    CitizenFacade citizenFacade;

    public TeacherManager() throws IOException {
        this.templatesFacade = new GetTemplatesFacade();
        this.citizenFacade = new CitizenFacade();
    }

    public ObservableList<Citizen> getTemplates() throws CitizenException {
        List<Citizen> citizens = templatesFacade.retrieveTemplates();
        ObservableList<Citizen> obsCitizens = FXCollections.observableArrayList();
        obsCitizens.addAll(citizens);
        return obsCitizens;
    }

    public void copyCitizenToDB(Citizen citizen, ArrayList<Student> students) throws CitizenException {
        citizenFacade.copyCitizenToDB(citizen, students);
    }

}
