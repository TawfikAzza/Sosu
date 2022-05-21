package bll;

import be.*;
import bll.exceptions.UserException;
import bll.util.GlobalVariables;
import dal.db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements UserInterface {

    private TeacherDAO teacherDao;
    private StudentDAO studentDao;
    private UsersDAO usersDAO;
    private SchoolDAO schoolDAO;
    private AdminDao adminDao;

    public UserManager() throws IOException {
        teacherDao = new TeacherDAO();
        studentDao = new StudentDAO();
        usersDAO = new UsersDAO();
        schoolDAO = new SchoolDAO();
        adminDao= new AdminDao();
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber)throws UserException {
        return teacherDao.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        return studentDao.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public List<Student>getAllStudents(String initials)throws SQLException{
        return studentDao.getAllStudents(initials,GlobalVariables.getCurrentSchool().getId());
    }

    public List<Teacher>getAllTeachers(String initials) throws SQLException{
        return teacherDao.getAllTeachers(initials,GlobalVariables.getCurrentSchool().getId());
    }

    public void deleteTeacher(Teacher teacher)throws SQLException{
        teacherDao.deleteTeacher(teacher);
    }

    public void deleteStudent(Student student) throws SQLException{
        studentDao.deleteStudent(student);
    }

    public void editStudent(School school, Student student) throws SQLException, UserException {
         studentDao.editStudent(school, student);
    }

    public void editTeacher(Teacher teacher,School school) throws  UserException {
        teacherDao.editTeacher(teacher,school);
    }

    public ObservableList<Student> getStudents() throws UserException {
        ArrayList<Student> students = studentDao.getAllStudentsFromDB(GlobalVariables.getCurrentSchool());
        ObservableList<Student> obsStuds = FXCollections.observableArrayList();
        obsStuds.addAll(students);
        return obsStuds;
    }

    @Override
    public User submitLogin(String username, String password) throws Exception {
        User user = usersDAO.compareLogins(username, password);
        setCurrentSchool(user);
        return user;
    }

    @Override
    public Student getStudent(Student selectedItem) throws SQLException {
        return studentDao.getStudent(selectedItem);
    }

    @Override
    public List<Admin> getAllAdmins(String initials) throws SQLException {
        return adminDao.getAllAdmins(initials);
    }


    private void setCurrentSchool(User user) throws SQLException {
        if (user != null) {
            GlobalVariables.setCurrentSchool(schoolDAO.getSchoolByUserID(user.getId()));//Setting the school of currently logged in user for use in operations
            System.out.println(GlobalVariables.getCurrentSchool().getName());
        }
    }


    public int isUserNameTaken(String userName) throws SQLException {
        return usersDAO.userNameTaken(userName);
    }
}
