package bll.util;

import be.Citizen;
import be.School;

public class GlobalVariables {

    private static Citizen selectedCitizen;
    private static School currentSchool;

    public static School getCurrentSchool() {
        return currentSchool;
    }

    public static void setCurrentSchool(School currentSchool) {
        GlobalVariables.currentSchool = currentSchool;
    }

    public static Citizen getSelectedCitizen() {
        return selectedCitizen;
    }

    public static void setSelectedCitizen(Citizen selectedCitizen) {
        GlobalVariables.selectedCitizen = selectedCitizen;
    }
}
