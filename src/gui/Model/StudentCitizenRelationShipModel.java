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
    private ObservableList<Citizen> citizensOfStudent;
    private ObservableList<Student> studentsOfCitizen;

    public StudentCitizenRelationShipModel() throws IOException {
        this.studentCitizenRelationshipManager = new StudentCitizenRelationshipManager();
        this.citizensOfStudent = FXCollections.observableArrayList();
        this.studentsOfCitizen = FXCollections.observableArrayList();
    }

    public ObservableList<Citizen> getObsListCit()
    {
        return citizensOfStudent;
    }


    public ObservableList<Student> getStudentsOfCitizen(Citizen citizen) throws CitizenStudentRelationException, CitizenException {
        ObservableList<Student> obsList = FXCollections.observableArrayList();
        obsList.addAll(studentCitizenRelationshipManager.getStudentsOfCitizen(citizen));
        return obsList;
    }

    public void setCitizensOfStudentObs(Student student) throws StudentException, CitizenException {
        ArrayList<Citizen> citizens = studentCitizenRelationshipManager.getCitizensOfStudent(student);
        citizensOfStudent.clear();
        citizensOfStudent.addAll(citizens);
    }


    public void assignCitizensToStudents(Citizen template, ArrayList<Student> students) throws CitizenException {
        studentCitizenRelationshipManager.assignCitizensToStudents(template,students);
    }

    public void removeRelation(Student student, Citizen toRemove) throws CitizenException {
        studentCitizenRelationshipManager.removeRelation(student, toRemove);
        citizensOfStudent.remove(toRemove);
    }

    public ObservableList<Citizen> getCitizensOfStudent(Student currentStudent) throws StudentException, CitizenException {
        setCitizensOfStudentObs(currentStudent);
        return citizensOfStudent;
    }
}
