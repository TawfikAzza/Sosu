package bll;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import dal.db.GetCitizensOfStudentFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class StudentManager {

    private GetCitizensOfStudentFacade getCitizensFacade;

    public StudentManager() throws IOException {
        this.getCitizensFacade = new GetCitizensOfStudentFacade();
    }

    public ArrayList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        return getCitizensFacade.getCitizensOfStudent(student);
    }
}
