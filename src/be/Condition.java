package be;

public class Condition {
    private int id;

    private String description;
    private String freeText;
    private String goal;
    private int citizenID;
    private int categoryID;
    private int status;
    public Condition(int id, int categoryID , int citizenID, String description, int status,String freeText, String goal) {
        this.id = id;
        this.categoryID = categoryID;
        this.citizenID = citizenID;
        this.description = description;
        this.status=status;
        this.freeText = freeText;
        this.goal=goal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(int citizenID) {
        this.citizenID = citizenID;
    }

    public int getCatgoryID() {
        return categoryID;
    }

    public void setCatgoryID(int catgoryID) {
        this.categoryID = catgoryID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
