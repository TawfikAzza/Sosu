package bll.util;

import be.Citizen;
import be.School;
import be.Student;

public class GlobalVariables {

    private static Citizen currentCitizen;
    private static School currentSchool;
    private static Student currentStudent;

    public static School getCurrentSchool() {
        return currentSchool;
    }

    public static void setCurrentSchool(School currentSchool) {
        GlobalVariables.currentSchool = currentSchool;
    }

    public static Citizen getSelectedCitizen() {
        return currentCitizen;
    }

    public static void setSelectedCitizen(Citizen selectedCitizen) {
        GlobalVariables.currentCitizen = selectedCitizen;
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static void setCurrentStudent(Student currentStudent) {
        GlobalVariables.currentStudent = currentStudent;
    }

    public static void resetVariables() {
        currentCitizen = null;
        currentStudent = null;
        currentSchool = null;
    }
}
