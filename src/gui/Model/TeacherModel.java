package gui.Model;

import be.Citizen;
import be.Student;
import be.Teacher;
import bll.TeacherManager;
import bll.exceptions.CitizenException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherModel {

    private final TeacherManager teacherManager;
    private ObservableList<Citizen> templates;

    public TeacherModel() throws IOException, CitizenException {
        this.teacherManager = new TeacherManager();
        retrieveTemplates();
    }

    public ObservableList<Citizen> getTemplatesObs()
    {
        return templates;
    }

    private void retrieveTemplates() throws CitizenException {
        this.templates = teacherManager.getTemplates();
    }

    public void copyCitizenToDB(Citizen template) throws CitizenException {
        teacherManager.copyCitizenToDB(template);
    }

    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        teacherManager.assignCitizensToStudents(template,students);
    }
}
