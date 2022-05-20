package be;

public class Teacher extends User{

    public Teacher(int id, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) {
        super(id, firstName, lastName, userName, passWord, email, phoneNumber);
    }

    public Teacher(int id, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber,int schoolId) {
        super(id, firstName, lastName, userName, passWord, email, phoneNumber,schoolId);
    }

    public Teacher (){}
}
