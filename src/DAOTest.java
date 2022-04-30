import be.AbilityCategory;
import be.HealthCategory;
import be.School;
import be.Student;
import dal.db.FunctionalAbilityDAO;
import dal.db.HealthConditionDAO;
import dal.db.StudentDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DAOTest {
    public static void main(String[] args) throws SQLException, IOException {
        // getAllCategories();
        //  getAllCategoriesTree();
        // getAllAbilityCategoriesTree();
        // editStudent(new Student(7,"test","test","test","dfd","sfsf",543));
    }
    private static void getAllCategories() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        healthConditionDAO.getAllMainHealthCategories();
    }

    private static void getAllCategoriesTree() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        List<HealthCategory> healthCategoryList = healthConditionDAO.getAllCategoriesTree();
        for (HealthCategory healthCategory: healthCategoryList) {
            System.out.println("id "+ healthCategory.getId()+ " name "+healthCategory.getName()+ " size: "+healthCategory.getSubCategories().size());
            for (HealthCategory subCategory: healthCategory.getSubCategories()) {
                System.out.println("id "+ subCategory.getId()+ " name "+subCategory.getName()+ " size: "+subCategory.getSubCategories().size());
            }
        }
    }

    private static void getAllAbilityCategoriesTree() throws IOException, SQLException {
        FunctionalAbilityDAO functionalAbilityDAO = new FunctionalAbilityDAO();
        List<AbilityCategory> abilityCategoryList = functionalAbilityDAO.getAllCategoriesTree();
        for (AbilityCategory abilityCategory: abilityCategoryList) {
            System.out.println("id "+ abilityCategory.getId()+ " name "+abilityCategory.getName()+ " size: "+abilityCategory.getSubCategories().size());
            for (AbilityCategory subCategory: abilityCategory.getSubCategories()) {
                System.out.println("id "+ subCategory.getId()+ " name "+subCategory.getName()+ " size: "+subCategory.getSubCategories().size());
            }
        }
    }

    private static void createStudent() throws IOException, SQLException {
        StudentDao studentDao = new StudentDao();
        for (Student student : studentDao.getAllStudents("sid")){
            System.out.println(student.getFirstName()+" "+student.getLastName()+"   school:"+student.getSchoolName());
        }
    }

    private static void deleteStudent(Student student) throws IOException, SQLException {
        StudentDao studentDao = new StudentDao();
        studentDao.deleteStudent(student);
    }

    private static void editStudent(Student student) throws IOException, SQLException {
        student.setEmail("darbouka@error.df");
        StudentDao studentDao = new StudentDao();
        studentDao.editStudent(student);
    }
}
