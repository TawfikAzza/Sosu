package bll;

import be.Citizen;
import be.Student;
import be.Teacher;
import bll.exceptions.CitizenException;
import dal.db.CitizenFacade;
import dal.db.GetTemplatesFacade;
import dal.db.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherManager {

    GetTemplatesFacade templatesFacade;
    CitizenFacade citizenFacade;

    public TeacherManager() throws IOException {
        this.templatesFacade = new GetTemplatesFacade();
        this.citizenFacade = new CitizenFacade();
    }

    public ObservableList<Citizen> getTemplates(Teacher currentTeacher) throws CitizenException {
        List<Citizen> citizens = templatesFacade.retrieveTemplates(currentTeacher);
        ObservableList<Citizen> obsCitizens = FXCollections.observableArrayList();
        obsCitizens.addAll(citizens);
        return obsCitizens;
    }

    public void copyCitizenToDB(Citizen template, ArrayList<Student> students) throws CitizenException {
        citizenFacade.copyCitizenToDB(template, students);
    }
}
