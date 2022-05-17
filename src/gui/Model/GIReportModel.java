package gui.Model;

import be.Citizen;
import bll.GIReportManger;
import bll.exceptions.CitizenException;
import bll.exceptions.GIReportException;

import java.sql.SQLException;
import java.util.HashMap;


public class GIReportModel {


    private GIReportManger giReportManger;

    public GIReportModel() throws GIReportException, CitizenException {
        giReportManger = new GIReportManger();
    }


    public HashMap<String, String> getGiReportManger(Citizen citizen) {
        return giReportManger.getGIReport(citizen);

    }



}



