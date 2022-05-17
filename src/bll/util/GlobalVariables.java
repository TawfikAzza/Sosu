package bll.util;

import be.*;

public class GlobalVariables {

    private static Citizen currentCitizen;
    private static School currentSchool;
    private static Student currentStudent;
    private static Admin currentAdmin;
    private static Teacher currentTeacher;

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

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        GlobalVariables.currentAdmin = currentAdmin;
    }

    public static Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public static void setCurrentTeacher(Teacher currentTeacher) {
        GlobalVariables.currentTeacher = currentTeacher;
    }

    public static void resetVariables() {
        currentCitizen = null;
        currentStudent = null;
        currentSchool = null;
        currentAdmin = null;
        currentTeacher = null;
    }


}
