package gui.Model;

import be.Citizen;
import be.Student;
import be.Teacher;
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

    public ObservableList<Citizen> getTemplates(Teacher currentTeacher) throws CitizenException {
        return teacherManager.getTemplates(currentTeacher);
    }

    public void copyCitizenToDB(Citizen template, ArrayList<Student> students) throws CitizenException {
        teacherManager.copyCitizenToDB(template, students);
    }
}
