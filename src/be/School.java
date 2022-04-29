package be;

import java.util.List;

public class School {
    List<Student>allStudents;
    List<Teacher>allTeachers;
    int id;
    String name;
    public School(int id, String name){
        this.id=id;
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
}
