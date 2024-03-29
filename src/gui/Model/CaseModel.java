package gui.Model;

import be.Case;
import be.Citizen;
import bll.CaseManager;
import bll.exceptions.CaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class CaseModel {

    private final CaseManager caseManager;
    private final ObservableList<Case> cases;

    public CaseModel() throws IOException {
        this.caseManager = new CaseManager();
        this.cases = FXCollections.observableArrayList();
    }

    public ObservableList<Case> getObsCases() throws CaseException {
        if(cases.isEmpty())
        {
            cases.addAll(getAllCases());
        }
        return cases;
    }

    public List<Case> getAllCases() throws CaseException {
        return caseManager.getAllCases();
    }

    public int addCase(Case newCase) throws CaseException {
        return caseManager.addCase(newCase);
    }

    public void setObsListToCitizen(Citizen citizen) throws CaseException {
        caseManager.getCitizenCase(citizen);
    }

    public Case getCitizenCase(Citizen citizen) throws CaseException {
        return caseManager.getCitizenCase(citizen);
    }

    public void editCase(int id, Case newCase) {
        caseManager.editCase(id, newCase);
    }
}
