package be;

import java.time.LocalDate;
import java.util.List;

public class CitizenReport {
    private int id;
    private String fName;
    private String lName;
    private String cprNumber;
    private String address;
    private int phoneNumber;
    private LocalDate birthDate;
    private List<Condition> healthConditions;
    private List<Ability> functionalAbilities;
    private List<GeneralInfo> generalInfo;

    public CitizenReport(int id, String fName, String lName, String cprNumber, String address, int phoneNumber, LocalDate birthDate) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.cprNumber = cprNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.healthConditions = healthConditions;
        this.functionalAbilities = functionalAbilities;
        this.generalInfo = generalInfo;
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
}
