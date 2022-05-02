package gui.Model;

import be.Citizen;
import bll.TeacherManager;
import bll.exceptions.CitizenException;

import java.io.IOException;
import java.util.List;

public class TeacherModel {

    TeacherManager teacherManager;

    public TeacherModel() throws IOException {
        this.teacherManager = new TeacherManager();
    }

    public List<Citizen> getTemplates() throws CitizenException {
        return teacherManager.getTemplates();
    }
}
