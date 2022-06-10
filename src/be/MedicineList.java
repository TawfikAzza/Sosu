package be;


public class MedicineList {
    private int id;
    private int citizenID;
    private String medicineList;

    public MedicineList(int id, int citizenID, String medicineList) {
        this.id = id;
        this.citizenID = citizenID;
        this.medicineList = medicineList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(int citizenID) {
        this.citizenID = citizenID;
    }

    public String getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(String medicineList) {
        this.medicineList = medicineList;
    }

    @Override
    public String toString() {
        return medicineList;
    }
}
