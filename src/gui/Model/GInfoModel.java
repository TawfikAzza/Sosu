package gui.Model;


import be.Citizen;
import be.InfoCategory;
import bll.GInfoManager;
import bll.exceptions.CitizenException;

import java.util.List;

public class GInfoModel {

    private GInfoManager generalInfoManager;

    public GInfoModel() throws CitizenException {
        generalInfoManager = new GInfoManager();
    }

    public List<InfoCategory> getGInfoCategories() throws CitizenException {
        return generalInfoManager.getGInfoCategories();
    }

    public void saveInformation(Citizen currentCitizen, InfoCategory selectedInfoCategory, String infoContent) throws CitizenException {
        generalInfoManager.saveInformation(currentCitizen,selectedInfoCategory,infoContent);
    }
}
