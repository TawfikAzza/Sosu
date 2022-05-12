package gui.Model;

import be.Citizen;
import be.InfoCategory;
import bll.GIReportManger;
import bll.exceptions.CitizenException;
import bll.exceptions.GeneralInfoException;


public class GIReportModel {


    private GIReportManger giReportManger;

    public GIReportModel() throws GeneralInfoException, CitizenException {
        giReportManger = new GIReportManger();
    }


    public void getGiReportManger(Citizen citizen, InfoCategory selectedInfoCategory) {
        giReportManger.getGIReport(citizen, selectedInfoCategory);
    }
}



