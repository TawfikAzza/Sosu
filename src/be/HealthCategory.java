package be;

import java.util.ArrayList;
import java.util.List;

public class HealthCategory {
    private int id;
    private String name;
    private String example;
    private int sid;
    private int position;
    private List<HealthCategory> subCategories = new ArrayList<>();

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


    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<HealthCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<HealthCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return name;
    }
}
