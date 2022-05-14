package bll;

import be.Citizen;
import be.Student;
import be.Teacher;
import bll.exceptions.CitizenException;
import bll.util.GlobalVariables;
import dal.db.CitizenFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherManager {

    private final CitizenFacade citizenFacade;

    public TeacherManager() throws IOException {
        this.citizenFacade = new CitizenFacade();
    }

    public ObservableList<Citizen> getTemplates() throws CitizenException {
        ObservableList<Citizen> obsCitizens = FXCollections.observableArrayList();
        return obsCitizens;
    }

    public void copyCitizenToDB(Citizen template) throws CitizenException {
        //citizenFacade.copyCitizenToDB(template);
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenFacade.assignCitizensToStudents(template,students);
    }
}
