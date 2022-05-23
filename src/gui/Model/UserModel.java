package gui.Model;


import be.*;
import bll.StudentCitizenRelationshipManager;
import bll.UserManager;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private final StudentCitizenRelationshipManager studentCitizenRelationshipManager;
    private static UserModel single_instance = null;
    UserManager userManager;
    ObservableList<Teacher> allTeachers;
    ObservableList<Student> allStudents;
    ObservableList<String> allCitizensStudent;
    ObservableList<Admin> allAdmins;


    private UserModel() throws IOException, UserException {
        this.userManager = new UserManager();
        this.studentCitizenRelationshipManager=new StudentCitizenRelationshipManager();
        initObsLists();
    }


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
        Student newStudent = userManager.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
        allStudents.add(newStudent);
        return newStudent;
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Teacher newTeacher =  userManager.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
        allTeachers.add(newTeacher);
        return newTeacher;
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        if (allTeachers!=null)
            allTeachers.remove(teacher);
        userManager.deleteTeacher(teacher);
    }

    public void deleteStudent(Student student) throws SQLException {
        if (allStudents!=null)
            allStudents.remove(student);
         userManager.deleteStudent(student);
    }

    public void editStudent(School school,Student student) throws SQLException, UserException {
        userManager.editStudent(school,student);
    }

    public void editTeacher(Teacher teacher,School school) throws  UserException {
        userManager.editTeacher(teacher,school);
    }

    public static UserModel getInstance() throws IOException, UserException {
        if (single_instance == null)
            single_instance = new UserModel();

        return single_instance;
    }

    public User submitLogin (String username , String password) throws Exception {
        return this.userManager.submitLogin(username,password);
    }

    public ObservableList<Student> getObsListStudents() throws UserException {
        return allStudents;
    }

    private void initObsLists() throws UserException {
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(userManager.getStudents());
        this.allStudents = students;
    }

    public int userNameTaken(String userName) throws SQLException {
        return userManager.isUserNameTaken(userName);
    }


    public ObservableList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        ObservableList<Citizen> obsList = FXCollections.observableArrayList();
        ArrayList<Citizen> citizens = studentCitizenRelationshipManager.getCitizensOfStudent(student);
        obsList.addAll(citizens);
        return obsList;
    }

    public Student getStudentInformation(Student selectedItem) throws SQLException {
        return userManager.getStudent(selectedItem);
    }

    public ObservableList<Admin> getAllAdmins(String initials) throws SQLException {
        allAdmins= FXCollections.observableArrayList();
        allAdmins.addAll(userManager.getAllAdmins(initials));
        return allAdmins;
    }

    public Admin newAdmin(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) {
    Admin newAdmin = userManager.newAdmin(school,firstName,lastName,userName,passWord,email,phoneNumber);
    allAdmins.add(newAdmin);
    return newAdmin;
    }
}
