package be;

public class Ability {
    private int id;
    private int score;
    private int categoryID;
    private int citizenID;
    private int status;
    private String goals;

    public Ability(int id, int categoryID,int citizenID, int score,int status) {
        this.id = id;
        this.categoryID=categoryID;
        this.citizenID=citizenID;
        this.score = score;
        this.status=status;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
