package gui.Model;

import bll.GIReportManger;
import bll.GInfoManager;
import bll.exceptions.GeneralInfoException;

public class GIReportModel {

    private GIReportManger giReportManger;

    public GIReportModel() throws GeneralInfoException {
        giReportManger = new GIReportManger();
    }


}
