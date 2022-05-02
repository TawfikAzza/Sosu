package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import dal.db.GetTemplatesFacade;

import java.io.IOException;
import java.util.List;

public class TeacherManager {

    GetTemplatesFacade templatesFacade;

    public TeacherManager() throws IOException {
        this.templatesFacade = new GetTemplatesFacade();
    }

    public List<Citizen> getTemplates() throws CitizenException {
        return templatesFacade.retrieveTemplates();
    }

}
