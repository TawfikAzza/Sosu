package bll;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import bll.util.GlobalVariables;
import dal.db.GetCitizensOfStudentFacade;
import dal.db.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentManager {

    private StudentDAO studentDAO;

    public StudentManager() throws IOException {
        studentDAO = new StudentDAO();
    }

    public ObservableList<Student> getAllStudents() throws UserException {
        return FXCollections.observableArrayList(studentDAO.getAllStudentsFromDB(GlobalVariables.getCurrentSchool()));
    }

    public void deleteStudent(Student selectedStudent) throws StudentException {
        try {
            studentDAO.deleteStudent(selectedStudent);
        } catch (SQLException e) {
            throw new StudentException("Error while deleting student",e);
        }
    }
}
