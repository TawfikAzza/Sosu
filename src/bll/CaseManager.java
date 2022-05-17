package bll;

import be.Case;
import be.Citizen;
import bll.exceptions.CaseException;
import dal.db.CaseDAO;

import java.io.IOException;
import java.util.List;

public class CaseManager {

    private CaseDAO caseDAO;

    public CaseManager() throws IOException {
        this.caseDAO = new CaseDAO();
    }

    public List<Case> getAllCases() throws CaseException {
        return caseDAO.getAllCases();
    }

    public void addCase(Case newCase) throws CaseException {
        caseDAO.addCase(newCase);
    }

    public Case getCitizenCase(Citizen citizen) throws CaseException {
        return caseDAO.getCitizenCase(citizen);
    }
}
