package be;

public class Case {

    private int id;
    private String caseName;
    private String content;

    public Case(int id, String content, String caseName) {
        this.id = id;
        this.content = content;
        this.caseName = caseName;
    }

    public Case(String caseName, String content)
    {
        this.caseName = caseName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return caseName;
    }
}
