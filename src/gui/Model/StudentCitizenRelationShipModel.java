package gui.Model;

import be.Citizen;
import be.Student;
import bll.StudentCitizenRelationshipManager;
import bll.exceptions.CitizenException;
import bll.exceptions.CitizenStudentRelationException;
import bll.exceptions.StudentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class StudentCitizenRelationShipModel {

    private final StudentCitizenRelationshipManager studentCitizenRelationshipManager;

    public StudentCitizenRelationShipModel() throws IOException {
        this.studentCitizenRelationshipManager = new StudentCitizenRelationshipManager();
    }

    public ObservableList<Student> getStudentsOfCitizen(Citizen citizen) throws CitizenStudentRelationException, CitizenException {
        ObservableList<Student> obsList = FXCollections.observableArrayList();
        obsList.addAll(studentCitizenRelationshipManager.getStudentsOfCitizen(citizen));
        return obsList;
    }

    public ObservableList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        ObservableList<Citizen> obsList = FXCollections.observableArrayList();
        ArrayList<Citizen> citizens = studentCitizenRelationshipManager.getCitizensOfStudent(student);
        obsList.addAll(citizens);
        return obsList;
    }
}
