package gui.Model;

import be.School;
import be.Teacher;
import bll.SchoolManager;
import bll.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolModel {

    SchoolManager schoolManager;
    ObservableList<School> allSchools;
    public SchoolModel() throws IOException {
        schoolManager = new SchoolManager();
    }
    public ObservableList<School>getAllSchools() throws SQLException {
        allSchools= FXCollections.observableArrayList();
        allSchools.addAll(schoolManager.getAllSchools());
        return allSchools;
    }
}
