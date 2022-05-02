package gui.Model;


import be.Citizen;
import be.InfoCategory;
import bll.GInfoManager;
import bll.exceptions.GeneralInfoException;

import java.util.List;

public class GInfoModel {

    private GInfoManager generalInfoManager;

    public GInfoModel() throws GeneralInfoException {
        generalInfoManager = new GInfoManager();
    }

    public List<InfoCategory> getGInfoCategories() throws GeneralInfoException {
        return generalInfoManager.getGInfoCategories();
    }

    public void saveInformation(Citizen currentCitizen, InfoCategory selectedInfoCategory, String infoContent) throws GeneralInfoException {
        generalInfoManager.saveInformation(currentCitizen,selectedInfoCategory,infoContent);
    }
}
