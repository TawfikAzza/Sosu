package be;

import java.util.List;

public class Citizen {
    private int id;
    private String fname;
    private String lname;
    private String cprNumber;
    private String address;
    private int phoneNumber;
    private String birthDate;
    private boolean isTemplate;
    private List<HealthCategory> healthCategories;
    private List<AbilityCategory> abilityCategories;
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

    public List<HealthCategory> getHealthConditions() {
        return healthCategories;
    }

    public void setHealthConditions(List<HealthCategory> healthConditions) {
        this.healthCategories = healthConditions;
    }

    public List<AbilityCategory> getFunctionalAbilities() {
        return abilityCategories;
    }

    public void setFunctionalAbilities(List<AbilityCategory> functionalAbilities) {
        this.abilityCategories = functionalAbilities;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
