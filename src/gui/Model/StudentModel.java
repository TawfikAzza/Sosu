package gui.Model;

import be.Citizen;
import be.Student;
import bll.StudentManager;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import gui.utils.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentModel {

    private StudentManager studentManager;

    private static StudentModel instance;

    private FilteredList<Student> studentObservableList;

    private StudentModel() throws UserException, IOException {
        this.studentManager = new StudentManager();
        initObsLists();
    }

    public static void resetInstance() {
        instance = null;
    }

    private void initObsLists() throws UserException {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        studentList.setAll(studentManager.getAllStudents());
        studentObservableList = new FilteredList<>(studentList,student -> true);
    }

    public static StudentModel getInstance() throws UserException, IOException {
        if (instance==null)
            instance = new StudentModel();
        return instance;
    }

    public FilteredList<Student> getObsStudents() {
        return studentObservableList;
    }

    public void deleteStudent(Student selectedStudent) throws StudentException {
        studentManager.deleteStudent(selectedStudent);
        ObservableList<Student> underlyingList = (ObservableList<Student>) getObsStudents().getSource();
        underlyingList.remove(selectedStudent);
    }

    public Student getStudentInformation(Student selectedItem) throws SQLException {
        return studentManager.getStudent(selectedItem);
    }
}
