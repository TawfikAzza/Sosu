package be;

import java.time.LocalDate;
import java.util.List;

public class Citizen {
    private int id;
    private String fname;
    private String lname;
    private String cprNumber;
    private String address;
    private int phoneNumber;
    private LocalDate birthDate;
    private boolean isTemplate;
    private List<Condition> healthConditions;
    private List<Ability> functionalAbilities;
    private List<GeneralInfo> generalInfo;

    public Citizen(int id, String fname, String lname, String cprNumber) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.cprNumber = cprNumber;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public List<Condition> getHealthConditions() {
        return healthConditions;
    }

    public void setHealthConditions(List<Condition> healthConditions) {
        this.healthConditions = healthConditions;
    }

    public List<Ability> getFunctionalAbilities() {
        return functionalAbilities;
    }

    public void setFunctionalAbilities(List<Ability> functionalAbilities) {
        this.functionalAbilities = functionalAbilities;
    }

    public List<GeneralInfo> getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(List<GeneralInfo> generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
