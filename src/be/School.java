package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class School{
    private int id;
    private String name;

    private StringProperty nameProperty;



    public School(int id, String name){
        this.id=id;
        this.name = name;
       nameProperty= new SimpleStringProperty(name);
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
        nameProperty.set(name);
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        return id == school.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }
}
