package dal.db;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CitizenDAOTest {


    /**
     * Author : Tawfik
     * Important Note :
     * For this Unit test I had to struggle to retrieve the right exceptions from the app.
     * and chances are if you are retrieving only failed test, it means that the database connections are working fine.
     * The way we implemented the exceptions in this program is such that the DAO will only return an exception when the connection to the database
     * is not available, as we place our catch on the try with resources...
     * I still implemented some of them in the UNIT test as proof of concept of use of JUNIT to catch exception with a set message.
     * It also means that if you want to have all the test passed with success, you have to not be connected to the school network and/or
     * have no access to the database associated to the program this UNIT is testing....
     * **/

    @DisplayName("Exception handling test for the Retrieval of template citizen associated to a school method")
    @Test
    void getCitizens() {
        //Arrange
        int currentSchoolID=0;
        boolean isTemplate = true;
        CitizenDAO citizenDAO = null;
        try {
            citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Act
        CitizenDAO finalCitizenDAO = citizenDAO;
        CitizenException ex = Assertions.assertThrows(CitizenException.class,()-> finalCitizenDAO.getCitizens(currentSchoolID,isTemplate));
        //Assert
        String actualMessage = ex.getMessage();
        String expectedMessage = "Could not retrieve citizens from DB";
        Assertions.assertEquals(expectedMessage,actualMessage);

    }
    /**
     * This test may be passed even if the database is accessible, the reason being
     * the id used during the test are non existent in the database and does not respect the foreign
     * key constraint established, the method will thus throw an exception.
     *
     * */
    @DisplayName("Exception handling test of the Assign Citizen to Student method")
    @Test
    void assignCitizensToStudents() {
        //Arrange
        Citizen citizen = new Citizen(0,"testCitizen","testCitizen");
        Student student = new Student(0,"testStudent","testStudent");
        ArrayList<Student> studentList =  new ArrayList<>();
        studentList.add(student);
        CitizenDAO citizenDAO= null;
        try {
            citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Act
        CitizenDAO finalCitizenDAO = citizenDAO;
        CitizenException ex = Assertions.assertThrows(CitizenException.class,()-> finalCitizenDAO.assignCitizensToStudents(citizen, studentList));
        //Assert
        String actualMessage = ex.getMessage();
        String expectedMessage = "Error connecting to DB";
        Assertions.assertEquals(expectedMessage,actualMessage);

    }


    @DisplayName("Exception handling test of the removal of a Citizen from a Student")
    @Test
    void removeRelation() {
        //Arrange
        Citizen citizen = new Citizen(0,"testCitizen","testCitizen");
        Student student = new Student(0,"testStudent","testStudent");

        CitizenDAO citizenDAO= null;
        try {
            citizenDAO = new CitizenDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Act
        CitizenDAO finalCitizenDAO = citizenDAO;
        CitizenException ex = Assertions.assertThrows(CitizenException.class,()-> finalCitizenDAO.removeRelation(student,citizen));
        //Assert
        String actualMessage = ex.getMessage();
        String expectedMessage = "Error removing relation";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }
}