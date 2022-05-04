package bll;

import be.School;
import be.Student;
import be.Teacher;
import be.User;
import bll.exceptions.UserException;
import dal.db.StudentDAO;
import dal.db.TeacherDAO;
import dal.db.UsersDAO;
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

    public UserManager() throws IOException {
        teacherDao = new TeacherDAO();
        studentDao = new StudentDAO();
        usersDAO = new UsersDAO();
    }

    public Teacher newTeacher(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber)throws UserException {
        return teacherDao.newTeacher(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public Student newStudent(School school, String firstName, String lastName, String userName, String passWord, String email, String phoneNumber) throws UserException {
        return studentDao.newStudent(school,firstName,lastName,userName,passWord,email,phoneNumber);
    }

    public List<Student>getAllStudents(String initials)throws SQLException{
        return studentDao.getAllStudents(initials);
    }

    public List<Teacher>getAllTeachers(String initials) throws SQLException{
        return teacherDao.getAllTeachers(initials);
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
        ArrayList<Student> students = studentDao.getAllStudentsFromDB();
        ObservableList<Student> obsStuds = FXCollections.observableArrayList();
        obsStuds.addAll(students);
        return obsStuds;
    }

    @Override
    public User submitLogin(String username, String password) throws Exception {

        User user = usersDAO.compareLogins(username, password);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public int isUserNameTaken(String userName) throws SQLException {
        return usersDAO.userNameTaken(userName);
    }
}
