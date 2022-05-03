package bll;

import be.School;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import dal.db.SchoolDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SchoolManager {
    SchoolDao schoolDao;

    public SchoolManager() throws SchoolException {
        try {
            schoolDao = new SchoolDao();
        } catch (IOException e) {
            throw new SchoolException("Error while connecting to the database",e);
        }
    }
    public List<School>getAllSchools() throws SchoolException {
        try {
            return schoolDao.getAllSchools();
        } catch (SQLException e) {
            throw new SchoolException("Error while retrieving schools from the database",e);
        }
    }
    public List<String>getAllStudents(School school) throws UserException {
        try {
            return schoolDao.getAllStudents(school);
        } catch (SQLException e) {
            throw new UserException("Error while retrieving users from the database",e);
        }
    }
    public List<String>getAllTeachers(School school) throws UserException{
        try {
            return schoolDao.getAllTeachers(school);
        } catch (SQLException e) {
            throw new UserException("Error while retrieving users from the database",e);
        }
    }

    public List<String>getAllCitizens(School school) throws UserException{
        try {
            return schoolDao.getAllCitizens(school);
        } catch (SQLException e) {
            throw new UserException("Error while retrieving users from the database",e);
        }
    }
    public School newSchool(String schoolName)throws SchoolException {
        return schoolDao.newSchool(schoolName);
    }

}
