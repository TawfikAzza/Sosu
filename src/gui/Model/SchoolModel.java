package gui.Model;

import be.School;
import be.Teacher;
import bll.SchoolManager;
import bll.UserManager;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SchoolModel {

    SchoolManager schoolManager;
    ObservableList<School> allSchools;
    ObservableList<String> allStudents;
    ObservableList<String> allTeachers;
    ObservableList<String> allCitizens;

    private FilteredList<School>allSchoolsFL;

    private static SchoolModel single_instance = null;

    private SchoolModel() throws SchoolException {
        schoolManager = new SchoolManager();
        initObs();
    }

    private void initObs() throws SchoolException {
        ObservableList<School>allSchools=FXCollections.observableArrayList();
        allSchools.addAll(getAllSchools());
        allSchoolsFL = new FilteredList<>(allSchools,school -> true);
    }

    public List<School> getAllSchools() throws SchoolException {
        return schoolManager.getAllSchools();
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
        School newSchool =  schoolManager.newSchool(text);
        ObservableList<School> underlyingList = ((ObservableList<School>) allSchoolsFL.getSource());
        underlyingList.add(newSchool);
        return newSchool;
    }

    public void deleteSchool(School selectedItem) throws SQLException {
        schoolManager.deleteSchool(selectedItem);
        ObservableList<School> underlyingList = (ObservableList<School>) allSchoolsFL.getSource();
        underlyingList.remove(selectedItem);
    }

    public static SchoolModel getInstance() throws IOException, UserException, SchoolException {
        if (single_instance == null)
            single_instance = new SchoolModel();

        return single_instance;
    }

    public static void resetInstance(){
        single_instance = null;
    }

    public void editSchool(School school,String name) throws SQLException, SchoolException {
        schoolManager.editSchool(school,name);
    }

    public FilteredList<School> getAllSchoolsFL() {
        return allSchoolsFL;
    }


}
