package gui.Model;


import be.School;
import be.Student;
import be.Teacher;
import be.User;
import bll.UserManager;
import bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class UserModel {


    private static UserModel single_instance = null;
    UserManager userManager;
    ObservableList<Teacher> allTeachers;
    ObservableList<Student> allStudents;

    public ObservableList<Teacher> getAllTeachers(String init) throws SQLException{
        allTeachers= FXCollections.observableArrayList();
        allTeachers.addAll(userManager.getAllTeachers(init));
        return allTeachers;
    }

    public ObservableList<Student> getAllStudents(String init) throws SQLException{
        allStudents= FXCollections.observableArrayList();
        allStudents.addAll(userManager.getAllStudents(init));
        return allStudents;
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        return userManager.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        return userManager.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        allTeachers.remove(teacher);
        userManager.deleteTeacher(teacher);
    }

    public void deleteStudent(Student student) throws SQLException {
        allStudents.remove(student);
         userManager.deleteStudent(student);
    }

    public void editStudent(Student student) throws SQLException, UserException {
        userManager.editStudent(student);
    }

    public void editTeacher(Teacher teacher,School school) throws  UserException {
        userManager.editTeacher(teacher,school);
    }



    public static UserModel getInstance() throws IOException {
        if (single_instance == null)
            single_instance = new UserModel();

        return single_instance;
    }

    public UserModel() throws IOException {
        this.userManager = new UserManager();
    }

    public User submitLogin (String username , String password) throws Exception {
        return this.userManager.submitLogin(username,password);
    }

    public ObservableList<Student> getStudents() throws UserException {
        return userManager.getStudents();
    }



}
