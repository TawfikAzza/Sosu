package bll;

import be.Citizen;
import be.MedicineList;
import bll.exceptions.MedicineListException;
import dal.db.MedicineListDAO;

import java.io.IOException;
import java.sql.SQLException;

public class MedicineListManger {

    MedicineListDAO medicineListDAO;

    public MedicineListManger() throws MedicineListException{
        try{
            medicineListDAO = new MedicineListDAO();

        } catch (IOException e) {
            throw new MedicineListException("Error while connecting to the database",e);
        }
    }


    public MedicineList getMedicineList(Citizen citizen) throws MedicineListException{
        try {
            return  medicineListDAO.getMedicineList(citizen);

        } catch (SQLException e) {
            throw new MedicineListException("Error while getting the medicine list from the database",e);
        }
    }


    public MedicineList addMedicineList(MedicineList medicineList) throws MedicineListException {
        try {
            medicineListDAO.addMedicineList(medicineList);
        } catch (SQLException e) {
            throw new MedicineListException("Error while getting the medicine list from the database",e);
        }
        return medicineList;
    }


    public MedicineList updateMedicineList(MedicineList medicineList) throws  MedicineListException {
        try {
              medicineListDAO.updateMedicineList(medicineList);
        } catch (SQLException e) {
            throw new MedicineListException("Error while getting the medicine list from the database",e);
        }

        return medicineList;
    }


}

