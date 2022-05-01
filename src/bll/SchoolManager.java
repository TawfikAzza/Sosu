package bll;

import be.School;
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
}
