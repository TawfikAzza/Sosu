package be;

public class GeneralInfo {
    private int id;
    private int citizenID;
    private int categoryID;
    private String content;

    public GeneralInfo(int id, int citizenID, int categoryID, String content) {
        this.id = id;
        this.citizenID = citizenID;
        this.categoryID = categoryID;
        this.content = content;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
