package be;

import java.util.List;

public class HealthCategory {
    private int id;
    private String name;
    private List<Condition> conditionList;

    public HealthCategory(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Condition> getDiseaseList() {
        return conditionList;
    }

    public void setDiseaseList(List<Condition> diseaseList) {
        this.conditionList = diseaseList;
    }
}
