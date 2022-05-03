package bll;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
import be.MedicineList;
import bll.exceptions.AbilityCategoryException;
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


}

