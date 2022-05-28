package gui.Model;

import be.*;
import bll.StudentCitizenRelationshipManager;
import bll.UserManager;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private final StudentCitizenRelationshipManager studentCitizenRelationshipManager;
    private static UserModel single_instance = null;
    UserManager userManager;

    private FilteredList<Teacher>teachers;
    private FilteredList<Student>students;
    private FilteredList<Admin>admins;


    private UserModel() throws IOException, UserException, SQLException {
        this.userManager = new UserManager();
        this.studentCitizenRelationshipManager=new StudentCitizenRelationshipManager();
        initObsLists();
    }

    public static void resetInstance() {
        single_instance = null;
    }


    public FilteredList<Teacher> getAllTeachers() throws SQLException{
        return teachers;
    }

    public List<Teacher>getAllTeachersList() throws SQLException {
        return userManager.getAllTeachers();
    }

    public List<Student> getAllStudentsList() throws SQLException, UserException {
        return userManager.getAllStudents();
    }

    public FilteredList <Student>getAllStudents()throws SQLException{
        return students;
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Student newStudent =  userManager.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
        ObservableList<Student> underlyingList = ((ObservableList<Student>) students.getSource());
        underlyingList.add(newStudent);

        return newStudent;
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Teacher newTeacher =  userManager.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
        ObservableList<Teacher> underlyingList = ((ObservableList<Teacher>) teachers.getSource());
        underlyingList.add(newTeacher);
        return newTeacher;
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        userManager.deleteTeacher(teacher);
        ObservableList<Teacher> underlyingList = (ObservableList<Teacher>) teachers.getSource();
        underlyingList.remove(teacher);
    }

    public void deleteStudent(Student student) throws SQLException {
        userManager.deleteStudent(student);
        ObservableList<Student> underlyingList = (ObservableList<Student>) students.getSource();
        underlyingList.remove(student);
    }

    public void editStudent(School school,Student student) throws SQLException, UserException {
        userManager.editStudent(school,student);
    }

    public void editTeacher(Teacher teacher,School school) throws  UserException {
        userManager.editTeacher(teacher,school);
    }

    public static UserModel getInstance() throws IOException, UserException, SQLException {
        if (single_instance == null)
            single_instance = new UserModel();

        return single_instance;
    }

    public User submitLogin (String username , String password) throws Exception {
        return userManager.submitLogin(username,password);
    }


    private void initObsLists() throws UserException, SQLException {
        ObservableList<Teacher> allTeachers = FXCollections.observableArrayList();
        ObservableList<Student>allStudents = FXCollections.observableArrayList();
        ObservableList<Admin>allAdmins = FXCollections.observableArrayList();

        allStudents.addAll(getAllStudentsList());
        allAdmins.addAll(getAllAdminsList());
        allTeachers.addAll(getAllTeachersList());

        teachers = new FilteredList<>(allTeachers, teacher -> true);
        students = new FilteredList<>(allStudents, student -> true);
        admins = new FilteredList<>(allAdmins, admin -> true);

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

    public FilteredList<Admin> getAllAdmins() throws SQLException {
        return admins;
    }
    public List<Admin>getAllAdminsList()throws SQLException{
        return userManager.getAllAdmins();
    }

    public Admin newAdmin(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        Admin newAdmin =  userManager.newAdmin(school,firstName,lastName,userName,passWord,email,phoneNumber);
        ObservableList<Admin> underlyingList = ((ObservableList<Admin>) admins.getSource());
        underlyingList.add(newAdmin);
        return newAdmin;
    }

    public void deleteAdmin(Admin selectedItem) throws SQLException {
        userManager.deleteAdmin(selectedItem);
        ObservableList<Admin> underlyingList = (ObservableList<Admin>) admins.getSource();
        underlyingList.remove(selectedItem);
    }

    public void editAdmin(School school, Admin admin) throws SQLException,UserException{
        userManager.editAdmin(school,admin);
    }

    public FilteredList<Teacher> getTeachers() {
        return teachers;
    }
}
