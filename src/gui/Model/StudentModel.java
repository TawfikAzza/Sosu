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

import java.io.IOException;
import java.util.ArrayList;

public class StudentModel {

    private StudentManager studentManager;

    private static StudentModel instance;

    private ObservableList<Student> studentObservableList;

    private StudentModel() throws UserException, IOException {
        this.studentManager = new StudentManager();
        initObsLists();
    }

    private void initObsLists() throws UserException {
        studentObservableList = FXCollections.observableArrayList();
        studentObservableList.setAll(studentManager.getAllStudents());
    }

    public static StudentModel getInstance() throws UserException, IOException {
        if (instance==null)
            instance = new StudentModel();
        return instance;
    }

    public ObservableList<Student> getObsStudents() {
        return studentObservableList;
    }
}
