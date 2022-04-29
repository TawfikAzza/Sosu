package be;

public class Ability {
    private int id;
    private String name;
    private int score;
    private int categoryID;
    private int citizenID;
    private String citizenText;

    public Ability(int id, String name, int score, int categoryID, int citizenID) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.categoryID = categoryID;
        this.citizenID = citizenID;
    }

    public String getCitizenText() {
        return citizenText;
    }

    public void setCitizenText(String citizenText) {
        this.citizenText = citizenText;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
