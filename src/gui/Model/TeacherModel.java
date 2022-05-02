package gui.Model;

import be.Citizen;
import be.Student;
import bll.TeacherManager;
import bll.exceptions.CitizenException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherModel {

    TeacherManager teacherManager;

    public TeacherModel() throws IOException {
        this.teacherManager = new TeacherManager();
    }

    public ObservableList<Citizen> getTemplates() throws CitizenException {
        return teacherManager.getTemplates();
    }

    public void copyCitizenToDB(Citizen citizen, ArrayList<Student> students) throws CitizenException {
        teacherManager.copyCitizenToDB(citizen, students);
    }
}
