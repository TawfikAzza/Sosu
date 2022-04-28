package be;

import java.util.List;

public class Citizen {
    private int id;
    private String name;
    private String cprNumber;
    private String address;
    private String phoneNumber;
    private String birthDate;
    private List<HealthCategory> healthCategories;
    private List<AbilityCategory> abilityCategories;
    private GeneralInfo generalInfo;

    public Citizen(int id, String name, String cprNumber) {
        this.id = id;
        this.name = name;
        this.cprNumber = cprNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
