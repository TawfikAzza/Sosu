package be;

public class InfoCategory {

    private int id;
    private String name;
    private String example;
    private String definition;

    public InfoCategory(int id, String name, String example, String definition) {
        this.id = id;
        this.name = name;
        this.example = example;
        this.definition = definition;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
