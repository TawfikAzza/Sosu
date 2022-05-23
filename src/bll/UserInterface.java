package bll;

import be.Admin;
import be.School;
import be.Student;
import be.User;
import bll.exceptions.UserException;
import javafx.collections.ObservableList;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface UserInterface {

    User submitLogin(String username, String password) throws Exception;

    Student getStudent(Student selectedItem) throws SQLException;

    List<Admin> getAllAdmins(String initials) throws SQLException;

    Admin newAdmin(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException;

    void deleteAdmin(Admin admin) throws SQLException;

    void editAdmin(School school, Admin admin) throws SQLException, UserException;
}
