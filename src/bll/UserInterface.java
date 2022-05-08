package bll;

import be.Student;
import be.User;

import java.sql.SQLException;

public interface UserInterface {

    User submitLogin(String username, String password) throws Exception;

    Student getStudent(Student selectedItem) throws SQLException;
}
