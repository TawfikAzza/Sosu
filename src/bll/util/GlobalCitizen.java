package bll.util;

import be.Citizen;

public class GlobalCitizen {

    private static Citizen selectedCitizen;

    public static Citizen getSelectedCitizen() {
        return selectedCitizen;
    }

    public static void setSelectedCitizen(Citizen selectedCitizen) {
        GlobalCitizen.selectedCitizen = selectedCitizen;
    }
}
