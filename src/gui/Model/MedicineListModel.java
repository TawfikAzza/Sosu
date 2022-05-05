package gui.Model;

import be.Citizen;
import be.MedicineList;
import bll.MedicineListManger;
import bll.exceptions.MedicineListException;

import java.sql.SQLException;

public class MedicineListModel {

    private MedicineListManger medicineListManger;

    public  MedicineListModel() throws MedicineListException {
        medicineListManger = new MedicineListManger();


    }


    public MedicineList getMedicineList(Citizen selectedCitizen) throws MedicineListException {
        return medicineListManger.getMedicineList(selectedCitizen);
    }


    public MedicineList updateMedicineList(MedicineList medicineList) throws MedicineListException, SQLException {
        return  medicineListManger.updateMedicineList(medicineList);

    }

    public MedicineList addMedicineList(MedicineList medicineList) throws MedicineListException {
        return  medicineListManger.addMedicineList(medicineList);
    }
}
