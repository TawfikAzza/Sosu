package gui.Model;


import be.School;
import be.Student;
import be.Teacher;
import be.User;
import bll.UserInterface;
import bll.UserManger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserModel {


    private static UserModel single_instance = null;
    UserManger userManger;
    ObservableList<Teacher> allTeachers;
    ObservableList<Student> allStudents;

    public ObservableList<Teacher>getAllTeachers(String init) throws SQLException{
        allTeachers= FXCollections.observableArrayList();
        allTeachers.addAll(userManger.getAllTeachers(init));
        return allTeachers;
    }

    public ObservableList<Student>getAllStudents(String init) throws SQLException{
        allStudents= FXCollections.observableArrayList();
        allStudents.addAll(userManger.getAllStudents(init));
        return allStudents;
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws SQLException {
        return userManger.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws SQLException {
        return userManger.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        allTeachers.remove(teacher);
        userManger.deleteTeacher(teacher);
    }

    public void deleteStudent(Student student) throws SQLException {
        allStudents.remove(student);
         userManger.deleteStudent(student);
    }

    public void editStudent(Student student) throws SQLException {
        userManger.editStudent(student);
    }

    public void editTeacher(Teacher teacher) throws SQLException {
        userManger.editTeacher(teacher);
    }



    public static UserModel getInstance() throws IOException {
        if (single_instance == null)
            single_instance = new UserModel();

        return single_instance;
    }

    private UserModel() throws IOException {
        this.userManger = new UserManger();
    }

    public User submitLogin (String username , String password) throws Exception {
        return this.userManger.submitLogin(username,password);
    }



}
