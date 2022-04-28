package be;

public class Condition {
    private int id;
    private String name;
    private String description;
    private String freeText;

    public Condition(int id, String name, String description, String freeText) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.freeText = freeText;
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
}
