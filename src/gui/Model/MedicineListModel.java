package gui.Model;

import be.Citizen;
import be.MedicineList;
import bll.MedicineListManger;
import bll.exceptions.MedicineListException;

public class MedicineListModel {

    private MedicineListManger medicineListManger;

    public  MedicineListModel() throws MedicineListException {
        medicineListManger = new MedicineListManger();


    }


    public MedicineList getMedicineList(Citizen selectedCitizen) throws MedicineListException {
        return medicineListManger.getMedicineList(selectedCitizen);
    }


    public void updateMedicineList(MedicineList medicineList) {

    }
}
