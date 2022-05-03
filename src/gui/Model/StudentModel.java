package gui.Model;

import be.Citizen;
import be.Student;
import bll.StudentManager;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import gui.utils.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class StudentModel {

    private StudentManager studentManager;

    public StudentModel() {
        try {
            this.studentManager = new StudentManager();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public ObservableList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        ObservableList<Citizen> obsList = FXCollections.observableArrayList();
        ArrayList<Citizen> citizens = studentManager.getCitizensOfStudent(student);
        obsList.addAll(citizens);
        return obsList;
    }
}
