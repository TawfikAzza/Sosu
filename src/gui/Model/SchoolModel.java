package gui.Model;

import be.School;
import be.Teacher;
import bll.SchoolManager;
import bll.UserManager;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolModel {

    SchoolManager schoolManager;
    ObservableList<School> allSchools;
    ObservableList<String> allStudents;
    ObservableList<String> allTeachers;
    ObservableList<String> allCitizens;


    public SchoolModel() throws SchoolException {
        schoolManager = new SchoolManager();
    }
    public ObservableList<School>getAllSchools() throws SQLException, SchoolException {
        allSchools= FXCollections.observableArrayList();
        allSchools.addAll(schoolManager.getAllSchools());
        return allSchools;
    }

    public ObservableList<String>getAllStudents(School school) throws SQLException, UserException {
        allStudents= FXCollections.observableArrayList();
        allStudents.addAll(schoolManager.getAllStudents(school));
        return allStudents;
    }

    public ObservableList<String>getAllTeachers(School school) throws SQLException, UserException {
        allTeachers= FXCollections.observableArrayList();
        allTeachers.addAll(schoolManager.getAllTeachers(school));
        return allTeachers;
    }

    public ObservableList<String>getAllCitizens(School school) throws SQLException, UserException {
        allCitizens= FXCollections.observableArrayList();
        allCitizens.addAll(schoolManager.getAllCitizens(school));
        return allCitizens;
    }

    public School newSchool(String text) throws SchoolException {
        return schoolManager.newSchool(text);
    }
}
