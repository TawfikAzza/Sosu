import be.*;
import bll.exceptions.UserException;
import dal.db.*;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOTest {
    public static void main(String[] args) throws SQLException, IOException {
        // getAllCategories();
        // getAllCategoriesTree();
        // getAllAbilityCategoriesTree();
        // editStudent(new Student(7,"test","test","test","dfd","sfsf",543));
        // createTeacher(new School(1,"SOSU ESBJERG"),"teacher","teacher","teacher","teacher","teacher@test.dk",9999999);
         //getAllTeachers("Aaza");
        // deleteTeacher(new Teacher(11,"teacher","teacher","teacher","teacher","teacher@test.dk",9999999));
        // editTeacher(new Teacher(12,"teacher","teacher","teacher","teacher","teacher@test.dk",9999999));
        // getAllAdmins();
        // getAllInfoCategories();
        //getAllSchools();
        //getAllConditionFromCitizen();
        //getAllAbilitiesFromCitizen();
        //checkIfInfoExists();
        //insertOrUpdateInfoTest();
    }

    private static void insertOrUpdateInfoTest() throws IOException, SQLException {
        Citizen citizen = new Citizen(38,"Test","test","123");
        InfoCategory infoCategory = new InfoCategory(4,"test","Example","Definition");
        String infoContent = "Some content";
        GInfoDAO gInfoDAO = new GInfoDAO();
        gInfoDAO.insertGeneralInformation(citizen,infoCategory,infoContent);
    }

    private static void checkIfInfoExists() throws IOException, SQLException {
        Citizen citizen = new Citizen(38,"Test","test","123");
        InfoCategory infoCategory = new InfoCategory(5,"test","Example","Definition");
        GInfoDAO gInfoDAO = new GInfoDAO();
        boolean result = gInfoDAO.checkIfInfoExists(citizen,infoCategory);
        System.out.println(result);

    }

    private static void getAllInfoCategories() throws IOException, SQLException {
        GInfoDAO gInfoDAO = new GInfoDAO();
        List<InfoCategory> infoCategories = gInfoDAO.getGInfoCategories();
        for (InfoCategory gInfoCategory : infoCategories) {
            System.out.println(gInfoCategory.getDefinition());
            System.out.println("Index of category + "+infoCategories.indexOf(gInfoCategory));
        }
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

    private static void editStudent(Student student) throws IOException, SQLException, UserException {
        student.setEmail("darbouka@error.df");
        StudentDao studentDao = new StudentDao();
        studentDao.editStudent(student);
    }

    private static void createTeacher(School school,String firstName,String lastName,String userName,String password,String email, String phoneNumber) throws IOException, UserException {
        TeacherDao teacherDao = new TeacherDao();
        teacherDao.newTeacher(school,firstName,lastName,userName,password,email,phoneNumber);
    }

    private static void getAllTeachers(String initials) throws IOException, SQLException {
        TeacherDao teacherDao = new TeacherDao();
        for (Teacher teacher : teacherDao.getAllTeachers(initials))
            System.out.println(teacher.getFirstName()+" "+teacher.getLastName()+"   School: "+teacher.getSchoolName());
    }

    private static void deleteTeacher(Teacher teacher) throws IOException, SQLException {
        TeacherDao teacherDao = new TeacherDao();
        teacherDao.deleteTeacher(teacher);
    }

    private static void editTeacher(Teacher teacher,School school) throws IOException, SQLException, UserException {
        teacher.setEmail("darbouka@error.df");
        TeacherDao teacherDao = new TeacherDao();
        teacherDao.editTeacher(teacher,school);
    }

    private static void getAllAdmins() throws IOException, SQLException {
        AdminDao adminDao = new AdminDao();
        for (Admin admin : adminDao.getAllAdmins())
            System.out.println(admin.getFirstName()+" "+admin.getLastName());
    }

    private static void getAllSchools() throws IOException, SQLException {
        SchoolDao schoolDao = new SchoolDao();
        for (School school : schoolDao.getAllSchools())
            System.out.println(school.getName());
    }
    private static void getAllConditionFromCitizen() throws IOException, SQLException {
        HealthConditionDAO healthConditionDAO = new HealthConditionDAO();
        HashMap <Integer, List<Pair<HealthCategory,Condition>>> hashMap = healthConditionDAO.getConditionsFromCitizen(1);


        for (Map.Entry<Integer, List<Pair<HealthCategory, Condition>>> entry : hashMap.entrySet()) {
            Integer sid = (Integer) entry.getKey();
            List<Pair<HealthCategory,Condition>> list = (List<Pair<HealthCategory,Condition>>)entry.getValue();
            for (Pair<HealthCategory,Condition> pair : list){
                System.out.println("SID : "+sid+" "+pair.getKey().getName()+" "+pair.getValue().getDescription());
            }
        }
    }
    private static void getAllAbilitiesFromCitizen() throws IOException, SQLException {
        FunctionalAbilityDAO functionalAbilityDAO = new FunctionalAbilityDAO();
        HashMap <Integer, List<Pair<AbilityCategory,Ability>>> hashMap = functionalAbilityDAO.getAbilitiesFromCitizen(1);


        for (Map.Entry<Integer, List<Pair<AbilityCategory, Ability>>> entry : hashMap.entrySet()) {
            Integer sid = (Integer) entry.getKey();
            List<Pair<AbilityCategory,Ability>> list = (List<Pair<AbilityCategory,Ability>>)entry.getValue();
            for (Pair<AbilityCategory,Ability> pair : list){
                System.out.println("SID : "+sid+" "+pair.getKey().getName()+" "+pair.getValue().getScore());
            }
        }
    }
}
