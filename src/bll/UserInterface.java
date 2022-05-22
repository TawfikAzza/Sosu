package bll;

import be.Admin;
import be.Student;
import be.User;
import javafx.collections.ObservableList;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface UserInterface {

    User submitLogin(String username, String password) throws Exception;

    Student getStudent(Student selectedItem) throws SQLException;

    List<Admin> getAllAdmins(String initials) throws SQLException;
}
