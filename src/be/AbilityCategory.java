package be;

import java.util.ArrayList;
import java.util.List;

public class AbilityCategory {
    private int id;
    private String name;
    private List<AbilityCategory> subCategories = new ArrayList<>();
    private int sid;
    private int position;

    public AbilityCategory(int id, String name) {
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

    public List<AbilityCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<AbilityCategory> subCategories) {
        this.subCategories = subCategories;
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
    @Override
    public String toString() {
        return name;
    }
}
