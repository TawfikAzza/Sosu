package be;

public abstract class User {
    private int id;
    private int schoolId;
    private String schoolName;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String email;
    private int phoneNumber;
    private int roleID;

    public User (int id, String firstName, String lastName,String userName,String passWord,String email,int phoneNumber){
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName= userName;
        this.passWord = passWord;
        this.email= email;
        this.phoneNumber= phoneNumber;
    }

    public User(int id, String userName, String passWord, String email, int roleID) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.roleID = roleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}