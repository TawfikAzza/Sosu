package bll;

import be.School;
import bll.exceptions.SchoolException;
import dal.db.SchoolDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SchoolManager {
    SchoolDao schoolDao;
    public SchoolManager() throws IOException {
        schoolDao = new SchoolDao();
    }
    public List<School>getAllSchools() throws SQLException {
        return schoolDao.getAllSchools();
    }
    public List<String>getAllStudents(School school) throws SQLException {
        return schoolDao.getAllStudents(school);
    }
    public List<String>getAllTeachers(School school) throws SQLException{
        return schoolDao.getAllTeachers(school);
    }

    public List<String>getAllCitizens(School school) throws SQLException{
        return schoolDao.getAllCitizens(school);
    }
    public School newSchool(String schoolName)throws SchoolException {
        return schoolDao.newSchool(schoolName);
    }

}
