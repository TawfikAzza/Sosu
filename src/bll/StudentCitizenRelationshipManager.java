package bll;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.CitizenStudentRelationException;
import bll.exceptions.StudentException;
import dal.db.GetCitizensOfStudentFacade;
import dal.db.GetStudentsOfCitizenFacade;

import java.io.IOException;
import java.util.ArrayList;

public class StudentCitizenRelationshipManager {

    private final GetStudentsOfCitizenFacade getStudentsOfCitizenFacade;
    private final GetCitizensOfStudentFacade getCitizensOfStudentFacade;

    public StudentCitizenRelationshipManager() throws IOException {
        this.getStudentsOfCitizenFacade = new GetStudentsOfCitizenFacade();
        this.getCitizensOfStudentFacade = new GetCitizensOfStudentFacade();
    }

    public ArrayList<Student> getStudentsOfCitizen(Citizen citizen) throws CitizenStudentRelationException, CitizenException {
        return getStudentsOfCitizenFacade.getStudentsOfCitizen(citizen);
    }

    public ArrayList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        return getCitizensOfStudentFacade.getCitizensOfStudent(student);
    }
}
