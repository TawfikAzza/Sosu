package gui.Model;


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
}
