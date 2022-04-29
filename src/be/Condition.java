package be;

public class Condition {
    private int id;
    private String name;
    private String description;
    private String freeText;
    private String citGoals;
    private int categoryID;
    private int citizenID;
    private int status;

    public Condition(int id, String name, String description, int categoryID, int citizenID, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryID = categoryID;
        this.citizenID = citizenID;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(int citizenID) {
        this.citizenID = citizenID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCitGoals() {
        return citGoals;
    }

    public void setCitGoals(String citGoals) {
        this.citGoals = citGoals;
    }
}
