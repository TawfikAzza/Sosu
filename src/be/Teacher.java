package be;

public class Teacher extends User{
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String email;
    private int phoneNumber;

    public Teacher(int id, String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) {
        super(id, firstName, lastName, userName, passWord, email, phoneNumber);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassWord() {
        return passWord;
    }

    @Override
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
