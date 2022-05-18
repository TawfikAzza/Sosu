package be;

public class Student extends User{

    public Student(int id, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) {
        super(id, firstName, lastName, userName, passWord, email, phoneNumber);
    }

    public Student(int id, int schoolId, String firstName, String lastName) {
        super(id, schoolId, firstName, lastName);
    }

    public Student(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

}
