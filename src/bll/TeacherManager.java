package bll;

import be.Citizen;
import bll.exceptions.CitizenException;
import dal.db.GetTemplatesFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class TeacherManager {

    GetTemplatesFacade templatesFacade;

    public TeacherManager() throws IOException {
        this.templatesFacade = new GetTemplatesFacade();
    }

    public ObservableList<Citizen> getTemplates() throws CitizenException {
        List<Citizen> citizens = templatesFacade.retrieveTemplates();
        ObservableList<Citizen> obsCitizens = FXCollections.observableArrayList();
        obsCitizens.addAll(citizens);
        return obsCitizens;
    }

}
