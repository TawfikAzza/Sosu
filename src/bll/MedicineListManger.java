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
            throw new MedicineListException("Error while getting th medicine list from the database",e);
        }
    }


    public void addMedicineList(MedicineList medicineList) throws SQLException {

    }


    public void updateMedicineList(MedicineList medicineList) throws SQLException {

    }



}

